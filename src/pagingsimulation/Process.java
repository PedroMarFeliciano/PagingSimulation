/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagingsimulation;

import java.util.ArrayList;
import java.util.Map;
import static pagingsimulation.PagingSimulation.availableMemory;

/**
 *
 * @author Pedro Feliciano
 */
public class Process {
    private int id, size, executionTime;
    private ArrayList<Tuple> memoryOccupied; // this arraylist
    
    Process(int id) {
        this.id = id;
        this.size = (int) (((Math.random()* 10000) % availableMemory) * 0.15) + 1;
        this.executionTime = (int) (Math.random() * 10000) % 200 + 1;
        
        memoryOccupied = new ArrayList<Tuple>();
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public int getExecutionTime() {
        return executionTime;
    }
    
    public void decreaseExecutionTime(int tick) {
        executionTime -= tick;
    }
    
    public void addMemoryPosition(int x, int y) {
        memoryOccupied.add(new Tuple(x,y));
    }
    
    public ArrayList<Tuple> getMemoryOccupied() {
        return memoryOccupied;
    }
    
    @Override
    public String toString() {
        return "ID: " + this.id + " - Tamanho: " + this.size
                + " - Tempo de execução: " + this.executionTime;
    }
}
