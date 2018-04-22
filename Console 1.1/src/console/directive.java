/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package console;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;

/**
 *
 * @author dr.gero
 */
public class directive extends Thread {
    
    String directive;
    List<DataOutputStream> consoleout = new ArrayList<>();
    TextArea dpolog, terminalwindow;

    directive(TextArea terminalwindow, TextArea dpolog, String directive, List<DataOutputStream> consoleout) {
        this.terminalwindow = terminalwindow;
        this.consoleout = consoleout;
        this.directive = directive;
        this.dpolog = dpolog;
    }
    
    @Override
    public void run() {
        try {
            consoleout.get(0).writeUTF(directive);
            terminalwindow.appendText("\n└─ ►◄");
            dpolog.appendText("\n" + directive);
        } catch (IOException ex) {
            Logger.getLogger(directive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
