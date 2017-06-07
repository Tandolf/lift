package se.andolf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas on 2016-09-04
 */
public class PatchRequest {

    private List<Patch> patches;

    public void add(Patch patch){
        if(patches == null)
            patches = new ArrayList<>();
        patches.add(patch);
    }

    public void remove(Patch patch){
        patches.stream()
                .filter(p -> !patch.getOp().equals(p.getOp()))
                .filter(p -> !patch.getPath().equals(p.getPath()))
                .filter(p -> !patch.getValue().equals(p.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        String string = "[";
        for (int i = 0; i < patches.size(); i++) {
            string += patches.get(i).toString();
            if(i != patches.size()-1)
                string += ",";
        }
        string += ']';
        return string;
    }

    public List<Patch> getPatches() {
        return patches;
    }

    public void setPatches(List<Patch> patches) {
        this.patches = patches;
    }
}
