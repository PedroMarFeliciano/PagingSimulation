/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagingsimulation;

import static pagingsimulation.PagingSimulation.availableMemory;
import static pagingsimulation.PagingSimulation.processesWaiting;
import static pagingsimulation.PagingSimulation.processesRunning;
import static pagingsimulation.PagingSimulation.memory;

/**
 *
 * @author Pedro Feliciano
 */
public class ProcessAllocation extends Thread {

    private int greatestLvlOfMultiprocessing = 0,
                deniedAllocation = 0;
    
    @Override
    public void run() {
        while (!processesWaiting.isEmpty()) {
            try {
                if (availableMemory >= processesWaiting.element().getSize()) {
                    System.out.println("Memória suficiente para alocar o processo de ID "
                            + processesWaiting.element().getId() + ".\n    Alocando o processo.");
                    processToMemory(processesWaiting.element().getSize());
                    System.out.println("    Processo " + processesWaiting.element().getId()
                            + " alocado e movido para a fila de execução.");
                }
                else {
                    deniedAllocation++;
                }
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    private void processToMemory(int size) {
        int processSize = size;

        for (int i = 0; i < (int) Math.sqrt(availableMemory); i++) {
            for (int j = 0; j < (int) Math.sqrt(availableMemory); j++) {
                if (memory[i][j] == -1) {
                    memory[i][j] = processesWaiting.element().getId();
                    
                    processesWaiting.element().addMemoryPosition(i, j);
                    
                    processSize--;
                }

                if (processSize == 0) {
                    break;
                }

            }
        }
        availableMemory -= size;
        System.out.println("Memória disponível diminuiu: "
                + processesWaiting.element().getSize() + " un. de memória."
                + " Total: " + availableMemory);
        processesRunning.add(processesWaiting.remove());
        
        if (processesRunning.size() > greatestLvlOfMultiprocessing) {
            greatestLvlOfMultiprocessing = processesRunning.size();
        }
    }
    
    public int getGreatestLvlOfMultiprocessing() {
        return greatestLvlOfMultiprocessing;
    }
    
    public int getDeniedAllocation() {
        return deniedAllocation;
    }
}
