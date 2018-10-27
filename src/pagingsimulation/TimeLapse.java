/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagingsimulation;

import static pagingsimulation.PagingSimulation.processesRunning;
import static pagingsimulation.PagingSimulation.processesTerminated;
import static pagingsimulation.PagingSimulation.processesWaiting;

/**
 *
 * @author Pedro Feliciano
 */
public class TimeLapse extends Thread {

    private int tick, wastedTime = 0, qtyOfProcesses;
    
    public TimeLapse(int qtyOfProcesses) {
        this.qtyOfProcesses = qtyOfProcesses;
    }

    @Override
    public void run() {
        while (processesTerminated.size() != qtyOfProcesses) {
            try {
                tick = (int) (Math.random() * 100) % 50 + 1;
                
                for (Process p : processesRunning) { //decreases time of all running processes
                    p.decreaseExecutionTime(tick);
                    System.out.println("Agora faltam " + p.getExecutionTime() + "un. de tempo para o processo " + p.getId());
                    // when time get below zero we count as wasted processor time 
                    if (p.getExecutionTime() < 0) {
                        wastedTime += Math.abs(p.getExecutionTime());
                    }
                }
            } catch (Exception e) {
                // do nothing
            }

            System.out.println("Passaram " + tick + " unidades de tempo.");
        }
    }

    public int getWastedTime() {
        return wastedTime;
    }
}
