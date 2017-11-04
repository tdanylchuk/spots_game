package com.tdanylchuk.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import com.tdanylchuk.app.model.Cell;
import com.tdanylchuk.app.model.Field;
import com.tdanylchuk.app.model.Part;
import com.tdanylchuk.app.sound.SoundPlayer;

import static com.tdanylchuk.app.GameplayConstants.ANIMATION_SPEED;
import static com.tdanylchuk.app.GameplayConstants.FIELD_CELLS_SIZE;

public class CellClickListener implements View.OnClickListener {

    private final Field field;
    private final int cellSize;
    private final Context context;
    private final SoundPlayer sp;
    private final int imageResId;

    private Cell current;

    public CellClickListener(Cell cell, Field field, int cellSize, Context context, SoundPlayer sp,
                             int imageResId) {
        this.current = cell;
        this.field = field;
        this.cellSize = cellSize;
        this.context = context;
        this.sp = sp;
        this.imageResId = imageResId;
    }

    @Override
    public void onClick(View v) {
        sp.play();
        try {
            if (current.isNotEmpty()) {
                int x = current.getX();
                int y = current.getY();
                if (x > 0) {
                    checkCell(x - 1, y);
                }
                if (x < FIELD_CELLS_SIZE - 1) {
                    checkCell(x + 1, y);
                }
                if (y > 0) {
                    checkCell(x, y - 1);
                }
                if (y < FIELD_CELLS_SIZE - 1) {
                    checkCell(x, y + 1);
                }
            }
        } catch (Exception e) {
        }
    }

    private void checkCell(int x, int y) throws Exception {
        Cell checking = field.cell(x, y);
        if (checking.isEmpty()) {
            Part currentPart = current.getPart();
            assert currentPart != null;
            View currentView = currentPart.getView();

            ObjectAnimator animX = ObjectAnimator.ofFloat(currentView, "x", x * cellSize);
            ObjectAnimator animY = ObjectAnimator.ofFloat(currentView, "y", y * cellSize);

            AnimatorSet animator = createAnimator();
            animator.playTogether(animX, animY);
            animator.start();

            current.setPart(checking.getPart());
            checking.setPart(currentPart);

            current = checking;

            throw new Exception();
        }
    }

    private AnimatorSet createAnimator() {
        AnimatorSet animator = new AnimatorSet();
        animator.setDuration(ANIMATION_SPEED);
        animator.addListener(new FinishCheckingListener(field, context, imageResId));
        return animator;
    }


}
