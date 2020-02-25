package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;

import java.io.IOException;
import java.util.ArrayList;

public interface IRepository {
    PrgState getCurrentProgramState();
    void logPrgStateExec(PrgState program) throws MyException;
    void deleteContent(PrgState program) throws MyException;
    ArrayList<PrgState> getProgramList();
    void setProgramList(ArrayList<PrgState> programList);
    void addProgramState(PrgState newProgramState);
    PrgState getProgramStateWithId(int currentId);

}
