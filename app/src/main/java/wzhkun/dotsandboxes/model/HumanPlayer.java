package wzhkun.dotsandboxes.model;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by wangzehao on 2015/7/6.
 */
public class HumanPlayer extends Player{
    private Line[] inputBuffer=new Line[1];
    private Object lock=new Object();

    public HumanPlayer(String name) {
        super(name);
    }

    public void add(Line line) {
        synchronized (inputBuffer){
            inputBuffer[0]=line;
            inputBuffer.notify();
        }
    }

    private Line getInput(){
        synchronized (inputBuffer){
            if(inputBuffer[0]!=null){
                Line temp=inputBuffer[0];
                inputBuffer[0]=null;
                return temp;
            }
            try {
                inputBuffer.wait();
            } catch (InterruptedException e) {
            }
            return this.getInput();
        }
    }

    @Override
    public Line move() {
        return getInput();
    }

}
