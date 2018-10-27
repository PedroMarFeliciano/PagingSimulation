/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagingsimulation;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Pedro Feliciano
 */
public class PagingSimulation {

    //memory definition
    public static int availableMemory = 1024;
    protected static int[][] memory = new int[(int) Math.sqrt(availableMemory)][(int) Math.sqrt(availableMemory)];
    //processes definition
    protected final int qtyOfProcesses = 200;
    protected static Queue<Process> processesWaiting = new ConcurrentLinkedQueue<>();
    protected static List<Process>  processesRunning = new LinkedList<>(),
                                    processesTerminated = new LinkedList<>();

    //statistics
    private int largestProcess = 0,
                smallestProcess = Integer.MAX_VALUE;
    
    
    ProcessAllocation processAllocator = new ProcessAllocation();
    ProcessDeallocation processDeallocator = new ProcessDeallocation(qtyOfProcesses);
    TimeLapse timeLapse = new TimeLapse(qtyOfProcesses);

    public static void main(String[] args) {
        new PagingSimulation();
    }

    public PagingSimulation() {
        prepareMemory();
        createProcesses();

        processAllocator.start();
        processDeallocator.start();
        timeLapse.start();
        
        
    }

    public void createProcesses() {
        for (int i = 0; i < qtyOfProcesses; i++) {
            Process p = new Process(i);
            System.out.println("Processo criado: " + p);
            
            if (p.getSize() > largestProcess) largestProcess = p.getSize();
            if (p.getSize() < smallestProcess) smallestProcess = p.getSize();
            
            processesWaiting.add(p);
        }
    }

    private void prepareMemory() {
        for (int i = 0; i < (int) Math.sqrt(availableMemory); i++) {
            for (int j = 0; j < (int) Math.sqrt(availableMemory); j++) {
                memory[i][j] = -1;
            }
        }
    }

}
