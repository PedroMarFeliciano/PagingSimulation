/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagingsimulation;

import static pagingsimulation.PagingSimulation.availableMemory;
import static pagingsimulation.PagingSimulation.memory;
import static pagingsimulation.PagingSimulation.processesRunning;
import static pagingsimulation.PagingSimulation.processesTerminated;
import static pagingsimulation.PagingSimulation.processesWaiting;

/**
 *
 * @author Pedro Feliciano
 */
public class ProcessDeallocation extends Thread {

    @Override
    public void run() {

        while (processesTerminated.size() != 200) {
            try {
                if (!processesRunning.isEmpty()) {

                    for (Process p : processesRunning) {
                        if (p.getExecutionTime() <= 0) {
                            deallocateProcess(p);
                            System.out.println("Processo " + p.getId() + " desalocado.");
                        }
                    }
                }
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    private void deallocateProcess(Process p) {
        for (int i = 0; i < (int) Math.sqrt(availableMemory); i++) {
            for (int j = 0; j < (int) Math.sqrt(availableMemory); j++) {
                if (memory[i][j] == p.getId()) {
                    memory[i][j] = -1;
                }
            }
        }
        availableMemory += p.getSize();
        System.out.println("Memória disponível aumentou " + p.getSize()
                + "un. de memória. Total: " + availableMemory);
        processesTerminated.add(p);
        processesRunning.remove(p);
    }

}
