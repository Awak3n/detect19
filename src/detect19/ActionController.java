package detect19;

import ADReNA_API.Data.DataSet;
import ADReNA_API.Data.DataSetObject;
import ADReNA_API.NeuralNetwork.Kohonen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ActionController implements ActionListener, ItemListener {

    MainClient main;

    ActionController(MainClient main) {
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer febre;
        Integer espirros;
        Integer narizEntupido;
        Integer dorDeCabeca;
        Integer faltaDeAr;
        if (e.getSource() == main.jm.jButton1) {
            if (main.jm.jComboBox1.getSelectedItem().toString().equals("Sim")) {
                febre = 1;
            } else {
                febre = 0;
            }
            if (main.jm.jComboBox2.getSelectedItem().toString().equals("Sim")) {
                espirros = 1;
            } else {
                espirros = 0;
            }
            if (main.jm.jComboBox3.getSelectedItem().toString().equals("Sim")) {
                narizEntupido = 1;
            } else {
                narizEntupido = 0;
            }
            if (main.jm.jComboBox4.getSelectedItem().toString().equals("Sim")) {
                dorDeCabeca = 1;
            } else {
                dorDeCabeca = 0;
            }
            if (main.jm.jComboBox5.getSelectedItem().toString().equals("Sim")) {
                faltaDeAr = 1;
            } else {
                faltaDeAr = 0;
            }
            showMessage(getDoenca(febre, espirros, narizEntupido, dorDeCabeca, faltaDeAr));
            System.out.println("" + febre + espirros + narizEntupido + dorDeCabeca + faltaDeAr);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    //atualiza o status
    public final void showMessage(final String text) {
        main.jm.jTextField1.setText(text);
    }

    private String getDoenca(Integer febre, Integer espirros, Integer narizEntupido, Integer dorDeCabeca, Integer faltaDeAr) {

        try {

            DataSet dataSet = new DataSet(5, 1);
            dataSet.Add(new DataSetObject(new double[]{1, 1, 1, 1, 1}, new double[]{1}));

            Kohonen kohonen = new Kohonen(5, 3);
            kohonen.Learn(dataSet);
            
            kohonen.Recognize(new double[]{});
            
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.toString());
        }

        return "TODO";
    }
    //controla os dados que chegam do servidor, marcando no campo do cliente
}