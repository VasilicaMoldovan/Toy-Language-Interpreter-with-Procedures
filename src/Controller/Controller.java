package Controller;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statement.IStmt;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository myController;
    private GarbageCollector myGarbageCollector;
    private ExecutorService executor;

    public Controller(IRepository repository){
        myController = repository;
        this.myGarbageCollector = new GarbageCollector();
    }

    public ArrayList removeCompletedProgram(ArrayList<PrgState> inProgramList){
        return (ArrayList)inProgramList.stream().filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public IStmt getOriginalProgram(){
        return myController.getCurrentProgramState().getOriginalProgram();
    }

    /*
    public void executeOneStep()
    {
        executor = Executors.newFixedThreadPool(8);
        ArrayList<PrgState> programs= myController.getProgramList();
        //print in the log file
        programs.forEach(program-> {
            try
            {
                myController.logPrgStateExec(program);
            }
            catch (MyException e)
            {
                System.out.println(e.toString());
            }
        });

        //prepare the list of callables
        var callList=programs.stream().map((PrgState p) -> (Callable<PrgState>)()->{return p.oneStep();}).collect(Collectors.toList());

        //start the execution of the callables
        List<PrgState> newProgramList=null;
        try
        {
            newProgramList=executor.invokeAll(callList).stream().
                    map(future-> {
                        try
                        {
                            return future.get();
                        }
                        catch(InterruptedException | ExecutionException e)
                        {
                            System.out.println(e.toString());
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

        }
        catch (InterruptedException e)
        {
            System.out.println(e.toString());
        }

        //add the new created threads to the list of existing threads
        programs.addAll(newProgramList);
        programs.forEach(program->
        {
            try
            {
                myController.logPrgStateExec(program);
                System.out.println(myController.getCurrentProgramState().toString()+"\n");
            }
            catch(MyException e)
            {
                System.out.println(e.toString());
            }
        });

        myController.setProgramList((ArrayList)programs);

        executor.shutdownNow();

    }*/

    public void executeOneStep()
    {
        executor = Executors.newFixedThreadPool(8);
        ArrayList<PrgState> programs = myController.getProgramList();

        programs.forEach(program->{
            try{
                myController.logPrgStateExec(program);
            }
            catch (MyException error){
                System.out.println(error.toString());
            }
        });


        //list of callable
        List<Callable<PrgState>> callList = programs.stream().map((PrgState p) -> (Callable<PrgState>) p::oneStep).collect(Collectors.toList());

        //start execution
        List<PrgState> newProgramList = null;
        try{
            newProgramList = executor.invokeAll(callList).stream().map(future-> {
                try{
                    return future.get();
                }
                catch (InterruptedException | ExecutionException error){
                    System.out.println(error.getMessage());
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        catch (InterruptedException error){
            System.out.println(error.getMessage());
        }

        //add the new thread to the list
        assert newProgramList != null;
        programs.addAll(newProgramList);
        programs.forEach(program->{
            try{
                myController.logPrgStateExec(program);
                System.out.println(myController.getCurrentProgramState().toString() + "\n");
            }
            catch (MyException error){
                System.out.println(error.getMessage());
            }
        });

        myController.setProgramList(programs);

        executor.shutdown();


    }


    public void oneStepForAllPrograms(List<PrgState> programs) throws MyException,InterruptedException
    {
        programs.forEach(program-> {
            try
            {
                myController.logPrgStateExec(program);
            }
            catch (MyException e)
            {
                System.out.println(e.toString());
            }
        });

        List<Callable<PrgState>> callList=programs.stream().map((PrgState p) -> (Callable<PrgState>) p::oneStep).collect(Collectors.toList());
        //List<Callable<PrgState>> callList = programs.stream().map((PrgState p) -> (Callable<PrgState>) p::oneStep).collect(Collectors.toList());

        List<PrgState> newProgramList=null;
        try
        {
            newProgramList=executor.invokeAll(callList).stream().
                    map(future-> {
                        try
                        {
                            return future.get();
                        }
                        catch(InterruptedException | ExecutionException e)
                        {
                            System.out.println(e.toString());
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

        }
        catch (InterruptedException e)
        {
            System.out.println(e.toString());
        }

        assert newProgramList != null;
        programs.addAll(newProgramList);
        programs.forEach(program->
        {
            try
            {
                myController.logPrgStateExec(program);
                System.out.println(myController.getCurrentProgramState().toString()+"\n");
            }
            catch(MyException e)
            {
                System.out.println(e.toString());
            }
        });
        myController.setProgramList((ArrayList)programs);
    }

    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(5);
        //remove the completed programs
        ArrayList prgList=removeCompletedProgram(myController.getProgramList());
        while(prgList.size() > 0){

            myController.getCurrentProgramState().getHeapTable().setContent(
                    myGarbageCollector.garbageCollector(
                            myGarbageCollector.addIndirectRef(myGarbageCollector.getAddressFromTables((ArrayList)myController.getProgramList()),myController.getCurrentProgramState().getHeapTable()),
                            myController.getCurrentProgramState().getHeapTable()));

            try {
                oneStepForAllPrograms(prgList);
            }catch (MyException | InterruptedException e){
                System.out.println("Exception");
            }

            prgList = removeCompletedProgram(myController.getProgramList());
        }
        executor.shutdownNow();
        myController.setProgramList((ArrayList)prgList);
    }

    public IRepository getRepository(){
        return myController;
    }
}
