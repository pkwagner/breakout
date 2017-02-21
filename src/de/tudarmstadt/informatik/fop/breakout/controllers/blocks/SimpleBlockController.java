package de.tudarmstadt.informatik.fop.breakout.controllers.blocks;

public class SimpleBlockController extends AbstractBlockController {

    public SimpleBlockController(String componentID) {
        super(componentID);
    }

    @Override
    public void setHitsLeft(int value) {

    }

    @Override
    public int getHitsLeft() {
        return 0;
    }

    @Override
    public void addHitsLeft(int value) {

    }

    @Override
    public boolean hasHitsLeft() {
        return false;
    }
}
