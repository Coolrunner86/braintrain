package com.github.coolrunner86.braintrain;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GameActivity extends Activity implements DataView.OnCellClickListener, View.OnClickListener{

    public enum DifficultyLevel {
        EASY,
        NORMAL
    }

    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;//TODO set difficulty level on run

    private DataView dataView;
    private DataManager dataManager;

    private Button undoBtn;
    private Button nextBtn;

    private int markIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        undoBtn = (Button) findViewById(R.id.undoBtn);
        undoBtn.setOnClickListener(this);

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        dataView = (DataView) findViewById(R.id.dataView);
        dataView.setOnCellClickListener(this);

        dataManager = new DataManager();
        dataManager.start(new NormalGenerator());//TODO give user choice

        dataView.setNewData(dataManager.getValues());
    }

    @Override
    public boolean onCellClick(int cellIndex) {
        if(cellIndex > dataManager.getValues().size() ||
           dataManager.getValues().get(cellIndex) == 0)
            return false;

        if(!isPairSatisfies(markIndex, cellIndex)) {
            dataView.setMarkOnDigit(cellIndex, markIndex != cellIndex);
            markIndex = (markIndex == cellIndex) ? -1 : cellIndex;
        }
        else {
            dataManager.removeValueAtIndex(markIndex);
            dataManager.removeValueAtIndex(cellIndex);

            dataView.setMarkOnDigit(markIndex, false);

            undoBtn.setEnabled(true);
            nextBtn.setEnabled(true);
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.undoBtn:
                if(difficultyLevel == DifficultyLevel.EASY) {
                    dataManager.undoLastAction();

                    //STUB
                    dataView.setNewData(dataManager.getValues());
                    //STUB
                }
                else {
                    dataManager.undoLevel();
                    dataView.setNewData(dataManager.getValues());
                }

                if(dataManager.levelsCount() == 0)
                    undoBtn.setEnabled(false);

                nextBtn.setEnabled(true);

                break;

            case R.id.nextBtn:
                dataManager.next();
                dataView.setNewData(dataManager.getValues());

                nextBtn.setEnabled(false);
                undoBtn.setEnabled(true);

                break;
        }
    }
    
    private boolean isPairSatisfies(int firstIndex, int secondIndex) {
        if(firstIndex == -1             || 
           secondIndex == -1            || 
           firstIndex == secondIndex    ||
           !isIndicesInCorrectPlace(firstIndex, secondIndex))
            return false;
        
        byte first = dataManager.getValues().get(firstIndex);
        byte second = dataManager.getValues().get(secondIndex);
        
        if(first == 0               || 
           second == 0              ||
           ((first + second) != 10) &&
           (first != second))
            return false;
                
        return true;
    }

    private boolean isIndicesInCorrectPlace(int firstIndex, int secondIndex) {
        if(Math.abs(firstIndex - secondIndex) == 1                      ||
           Math.abs(firstIndex - secondIndex) == DataView.DIGITS_IN_ROW)
            return true;

        if(firstIndex > secondIndex) {
            int tmp = firstIndex;
            firstIndex = secondIndex;
            secondIndex = tmp;
        }

        if(isIndicesVertical(firstIndex, secondIndex) || isIndicesHorizontal(firstIndex, secondIndex))
            return true;

        return false;
    }

    private boolean isIndicesVertical(int firstIndex, int secondIndex) {
        do {
            firstIndex += DataView.DIGITS_IN_ROW;

            if(dataManager.getValues().get(firstIndex) != 0)
                break;

        } while(firstIndex < secondIndex);

        if(firstIndex == secondIndex)
            return true;

        return false;
    }

    private boolean isIndicesHorizontal(int firstIndex, int secondIndex) {
        do {
            firstIndex++;

            if(dataManager.getValues().get(firstIndex) != 0)
                break;

        } while(firstIndex < secondIndex);

        if(firstIndex == secondIndex)
            return true;

        return false;
    }
}
