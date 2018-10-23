/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pricecreator;

/**
 *
 * @author yura_
 */
public class PriceCreator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            if (args.length==0){
                Scheduler task = new Scheduler();
            }else {
                if (args[0].equals("settings")){
                    CreatorSettings settings = new CreatorSettings();
                    settings.setVisible(true);            
                }
            }
    }
    
}
