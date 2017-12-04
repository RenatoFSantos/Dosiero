package util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import _model.domain.EParameterDate;

public class Funcoes
{
	
	public static final SimpleDateFormat FMDT_DDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");
	
    public static String sendMail(String mailServer, String subject,String to, String from, String mensagem) throws AddressException, MessagingException {  
        
        Properties props = System.getProperties();  
 
        props.put("mail.smtp.host",mailServer);   
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");   
        props.put("mail.smtp.debug", "true");   
        props.put("mail.mime.charset", "ISO-8859-1");   
        props.put("mail.smtp.port", "465");   
        props.put("mail.smtp.starttls.enable", "true");   
        props.put("mail.smtp.socketFactory.port", "465");   
        props.put("mail.smtp.socketFactory.fallback", "false");   
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
          
        Session session = Session.getDefaultInstance(props);//recebe props  
                      
        InternetAddress destinatario = new InternetAddress (to);  
        InternetAddress remetente = new InternetAddress (from);  
  
        Message msg = new MimeMessage(session);  
        msg.setSentDate(new Date());//novo  
        msg.setFrom(remetente);  
        msg.setRecipient( Message.RecipientType.TO, destinatario );  
        msg.setSubject (subject);  
        msg.setContent (mensagem.toString(), "text/HTML");  
      
        Transport transport = session.getTransport("smtp");  
        transport.connect(mailServer,"adm.dosiero@gmail.com","admDosiero");  
        msg.saveChanges();  
        transport.sendMessage(msg, msg.getAllRecipients());  
        transport.close();  
        return  "";  
    }  
    

    public static Date getPegaDataAtual() {  
        Calendar calendar = new GregorianCalendar();  
        Date date = new Date();  
        calendar.setTime(date);  
        return calendar.getTime();  
    }  
  
	public static Double arredondaValor(Double value, int nrCasas)
	{
		return round(value, nrCasas, BigDecimal.ROUND_HALF_UP);
	}

	private static Double round(double numero, int nrCasas, int tipo)
	{

		double fator = Math.pow(10, nrCasas);
		return new Double(Math.round(numero * fator) / fator);
	}

	public static Date addDayInDate(int day, Date date) throws Exception
	{

		if (date != null)
		{
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, day);

			return FMDT_DDMMYYYY.parse(FMDT_DDMMYYYY.format(calendar.getTime()));
		}
		else
		{
			return null;
		}

	}
	
	public static Date addMonthInDate(int mes, Date date) throws Exception {
		
		if (date != null) {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, mes);

			return FMDT_DDMMYYYY.parse(FMDT_DDMMYYYY.format(calendar.getTime()));
		}
		else
		{
			return null;
		}
	}
	
	public static Date addYearInDate(int ano, Date date) throws Exception {
		
		if (date != null) {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, ano);

			return FMDT_DDMMYYYY.parse(FMDT_DDMMYYYY.format(calendar.getTime()));
		}
		else
		{
			return null;
		}
	}

	public static String formataMoeda(Double valor)
	{

		String vl = "0,00";
		NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		try
		{
			if (valor != null)
			{
				vl = moneyFormat.format(valor).replace("R$", "");
			}
		}
		catch (Exception e)
		{
			return null;
		}

		return vl;
	}

	public static String zeroNaEsquerda(Integer numero) {
		return String.format("%09d", numero);
	}
	
	public static TimeZone getTimeZone()
	{
		return TimeZone.getDefault();
	}
	
	public static Integer diffDate(Date dataDe, Date dataAte, String formato) throws Exception {
		Integer result = 0;
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dtIni = sdf.parse(sdf.format(dataAte));
		Date dtFim = sdf.parse(sdf.format(dataDe));	    
	    if(formato.equals("SG")) { // ---- SEGUNDOS
			Long diferencaSegundos = (dtIni.getTime() - dtFim.getTime()) / (1000);
			result = Integer.valueOf(diferencaSegundos.toString());
		}
		if(formato.equals("MT")) {
			Long diferencaMinutos = (dtIni.getTime() - dtFim.getTime()) / (1000*60);
			result = Integer.valueOf(diferencaMinutos.toString());
		}
		if(formato.equals("HR")) {
		    Long diferencaHoras = (dtIni.getTime() - dtFim.getTime()) / (1000*60*60);
		    result = Integer.valueOf(diferencaHoras.toString());
		}
		if(formato.equals("DI")) {
		    Long diferencaDias = (dtIni.getTime() - dtFim.getTime()) / (1000*60*60*24);
		    result = Integer.valueOf(diferencaDias.toString());
		}
		if(formato.equals("MS")) {
		    Long diferencaMeses = (dtIni.getTime() - dtFim.getTime()) / (1000*60*60*24) / 30;
		    result = Integer.valueOf(diferencaMeses.toString());
		}
		if(formato.equals("AN")) {
		    Long diferencaAnos = ((dtIni.getTime() - dtFim.getTime()) / (1000*60*60*24) / 30) / 12;
		    result = Integer.valueOf(diferencaAnos.toString());
		}
		return result;
	}

	public static int compareDate(Date paramDate1, Date paramDate2) throws Exception
	 {

	  SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	  Date dt1 = formatDate.parse(formatDate.format(paramDate1));
	  Date dt2 = formatDate.parse(formatDate.format(paramDate2));

	  int result = 0;

	  if (dt1.before(dt2))
	  {
		  // --- RETORNA 1 - Se dt1 for anterior a dat2
		  result = EParameterDate.date_smaller.ordinal();

	  }
	  else if (dt1.after(dt2))
	  {
		  // --- RETORNA 2 - Se dt1 for posterior a dat2
		  result = EParameterDate.date_more.ordinal();

	  }
	  else if (dt1.equals(dt2))
	  {
		  // --- RETORNA 0 - Se dt1 for igual a dat2
		  result = EParameterDate.date_equal.ordinal();
	  }
	  return result;
	 }
	
	 public static boolean validaEmail(String email) {
	    Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"); 
	    Matcher m = p.matcher(email); 
	    if (m.find()){
	      return true;
	    }
	    else{
	      return false;
	    }  
	}
	 
    public static boolean validaCNPJ(String cnpj) {  
	      boolean ret = false;
	      cnpj = cnpj.replaceAll("[^0-9]", ""); // RETIRA TODAS AS PONTUAÇÕES
	      String base = "00000000000000";  
	      if (cnpj.length() <= 14) {  
	         if (cnpj.length() < 14) {  
	            cnpj = base.substring(0, 14 - cnpj.length()) + cnpj;  
	         }  
	  
	         int soma = 0;  
	         int dig = 0;  
	         String cnpj_calc = cnpj.substring(0, 12);  
	         char[] chr_cnpj = cnpj.toCharArray();  
	         // Primeira parte  
	         for (int i = 0; i < 4; i++)  
	            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)  
	               soma += (chr_cnpj[i] - 48) * (6 - (i + 1));  
	         for (int i = 0; i < 8; i++)  
	            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)  
	               soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));  
	         dig = 11 - (soma % 11);  
	         cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);  
	         // Segunda parte  
	         soma = 0;  
	         for (int i = 0; i < 5; i++)  
	            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)  
	               soma += (chr_cnpj[i] - 48) * (7 - (i + 1));  
	         for (int i = 0; i < 8; i++)  
	            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)  
	               soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));  
	         dig = 11 - (soma % 11);  
	         cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);  
	         ret = cnpj.equals(cnpj_calc);  
	  
	      }  
 
	      return ret;  
	} 
    
    public String gerarNovaSenha() {
        String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                    "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
                    "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                    "W", "X", "Y", "Z" };
       
        String senha = "";
       
        for (int x = 0; x < 10; x++) {
             int j = (int) (Math.random() * carct.length);
             senha += carct[j];
       
        }
       
        return senha;
      }

}
