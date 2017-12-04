package util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.JOptionPane;

public class Bematech {
	
	/*
	 * ******************************************************
	 * ROTINAS PARA IMPRESSORA NÃO FISCAL BEMATECH_MP4200TH *
	 * ******************************************************
	*/
	
	private static PrintService impressora;

	
    public static List<String> retornaImpressoras(){
        try {
            List<String> listaImpressoras = new ArrayList<String>();
            DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;  
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);  
            for (PrintService p : ps) {  
                listaImpressoras.add(p.getName());     
            }  
            return listaImpressoras;
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;
    }

    public static PrintService detectaImpressoras(String impressoraSelecionada) {  //Passa por parâmetro o nome salvo da impressora
            try {  
                DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
                PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);  
                for (PrintService p : ps) {  
                    if(p.getName()!=null && p.getName().contains(impressoraSelecionada)){  
                        impressora = p;  
                    }     
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }
            return impressora;
    }
    
    public static void acionarGuilhotina(PrintService iprint) {
    	// --- Bematech_MP4200TH
        imprime(""+(char)27+(char)109, iprint);
    }

	public static  boolean imprime(String texto, PrintService iprint) {  
        if (iprint == null) {  
            JOptionPane.showMessageDialog(null, "Nennhuma impressora foi encontrada. Instale uma impressora padrão \r\n(Generic Text Only) e reinicie o programa."); 
        } else {  
            try {  
                DocPrintJob dpj = iprint.createPrintJob();  
                InputStream stream = new ByteArrayInputStream((texto + "\n").getBytes());  
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;  
                Doc doc = new SimpleDoc(stream, flavor, null);  
                dpj.print(doc, null);  
                return true;  
            } catch (PrintException e) {  
                e.printStackTrace();  
            }  
        }  
        return false;  
    }
	
	public static  boolean imprimeDefault(String texto) {
		PrintService iprint;
		iprint = PrintServiceLookup.lookupDefaultPrintService();
        if (iprint == null) {  
            JOptionPane.showMessageDialog(null, "Nennhuma impressora foi encontrada. Instale uma impressora padrão \r\n(Generic Text Only) e reinicie o programa."); 
        } else {  
            try {  
                DocPrintJob dpj = iprint.createPrintJob();  
                InputStream stream = new ByteArrayInputStream((texto + "\n").getBytes());  
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;  
                Doc doc = new SimpleDoc(stream, flavor, null);  
                dpj.print(doc, null);  
                return true;  
            } catch (PrintException e) {  
                e.printStackTrace();  
            }  
        }  
        return false;  
    }

	
	public static String removeAcentos(String str) {
		CharSequence cs = new StringBuilder(str == null ? "" : str);
		return Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
}
