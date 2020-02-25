package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class InMemRepo implements IRepository {
    private ArrayList<PrgState> myRepo;
    private String  logFilePath;

    public InMemRepo(){
        myRepo = new ArrayList<>();
        logFilePath = "";
    }

    public InMemRepo(String path){
        myRepo = new ArrayList<>();
        logFilePath = path;
    }

    public InMemRepo(ArrayList<PrgState> myRepo) {
        this.myRepo = myRepo;
        this.logFilePath = "";
    }

    public InMemRepo(ArrayList<PrgState> myRepo, String path) {
        this.myRepo = myRepo;
        this.logFilePath = path;
    }

    @Override
    public PrgState getCurrentProgramState(){
        return myRepo.get(myRepo.size() - 1);
    }

    @Override
    public void logPrgStateExec(PrgState program) throws MyException{
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.write(program.toString());
            logFile.close();
        }
        catch (IOException error){
            throw new MyException(error.getMessage());
        }
    }

    @Override
    public void deleteContent(PrgState program) throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.write("");
            logFile.close();
        }
        catch (IOException error){
            throw new MyException("invalid file");
        }
    }

    @Override
    public ArrayList<PrgState> getProgramList(){
        return this.myRepo;
    }

    @Override
    public void setProgramList(ArrayList<PrgState> programList){
        this.myRepo = programList;
    }

    @Override
    public void addProgramState(PrgState newProgramState){
        myRepo.add(newProgramState);
    }

    @Override
    public PrgState getProgramStateWithId(int currentId){
        for(PrgState program : myRepo )
            if (program.getThreadId() == currentId)
                return program;
        return null;
    }
}
