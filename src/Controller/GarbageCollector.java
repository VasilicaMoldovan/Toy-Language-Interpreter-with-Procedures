package Controller;

import Model.DataStructures.IHeap;
import Model.PrgState;
import Model.Values.RefValue;
import Model.Values.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GarbageCollector {

    Map<Integer, Value>  garbageCollector(List<Integer> symTblAddresses, IHeap heap){
        Set<Map.Entry<Integer, Value>> heapSet = heap.getEntrySet();

        return heapSet.stream()
                .filter(e -> symTblAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddressFromTables(List<PrgState> programs){
        return programs.stream()
                .flatMap(prgState -> prgState.getSymTable().getContent().stream())
                .collect(Collectors.toList())
                .stream()
                .filter(element -> element instanceof RefValue)
                .map(element -> ((RefValue) element).getAddr())
                .collect(Collectors.toList());
    }


    public List<Integer> getSymTableAddress(List<Value> values){
        return values.stream()
                .filter(e -> e instanceof RefValue)
                .map(e -> ((RefValue)e).getAddr())
                .collect(Collectors.toList());
    }

    public List<Integer> addIndirectRef(List<Integer> symTableAddresses, IHeap heap){
        boolean change = false;
        Set<Map.Entry<Integer, Value>> heapSet = heap.getEntrySet();
        List<Integer> newListOfAddresses = symTableAddresses.stream().collect(Collectors.toList());

        while (!change){
            List<Integer> auxList = null;
            change = true;
            auxList = heapSet.stream()
                    .filter(e -> e.getValue() instanceof RefValue)
                    .filter(e -> newListOfAddresses.contains(e.getKey()))
                    .map(e -> (((RefValue)e.getValue()).getAddr()))
                    .filter(e -> !(newListOfAddresses.contains(e)))
                    .collect(Collectors.toList());
            if ( !auxList.isEmpty() ){
                change = false;
                newListOfAddresses.addAll(auxList);
            }
        }
        return newListOfAddresses;
    }

}
