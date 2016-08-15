package com.github.coolrunner86.braintrain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by flex on 04.08.16.
 */
public class DataManager {

    private Deque<DataState> stack = new LinkedList<DataState>();
    private DataState last = null;

    public void start(PatternGenerator generator) {
        Byte[] data = generator.generatePattern();

        if(!stack.isEmpty())
            stack.clear();

        last = new DataState(Arrays.asList(data));
    }

    public void next() {
        stack.push(last);

        List<Byte> values = last.getData();
        List<Byte> newValues = new ArrayList<Byte>(values);

        for(Byte value : values)
        {
            if(value == 0)
                continue;

            newValues.add(value);
        }

        last = new DataState(newValues);
    }

    public void undoLevel() {
        if(!stack.isEmpty())
            last = stack.pop();
    }

    public void undoLastAction() {
        //TODO

        //STUB
        undoLevel();
    }

    public int levelsCount() {
        return stack.size();
    }

    public List<Byte> getValues() {
        return last.getData();
    }

    public void removeValueAtIndex(int index) {
        last.removeAtPos(index);
    }

    public void load() {
        //TODO
    }

    public void save() {
        //TODO
    }
}
