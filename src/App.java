import java.util.regex.Pattern;
import java.util.*;

public class App {

    static final String FEV = "02";

    static Map<String, String> meses = new HashMap<String, String>() {
        {
            put("01", "janeiro");
            put("02", "fevereiro");
            put("03", "marco");
            put("04", "abril");
            put("05", "maio");
            put("06", "junho");
            put("07", "julho");
            put("08", "agosto");
            put("09", "setembro");
            put("10", "outubro");
            put("11", "novembro");
            put("12", "dezembro");
        }
    };

    static Set<String> badYears = new HashSet<String>();

    public static void main(String[] args) {

        populateBadYears();

        // String inputedDate = "30/02/1996";
        System.out.println("| --------------------------------------------------- ");
        System.out.println("| Conversor de datas ");
        System.out.println("| --------------------------------------------------- ");
        System.out.println("| Digite a data desejada no formato: XX/XX/XXXX ");
        System.out.println("| Respeite DD/MM/AAAA considere anos bissextos ");
        System.out.println("| --------------------------------------------------- ");

        Scanner sc = new Scanner(System.in);
        String inputedDate = null;
        if (sc.hasNextLine()) {
            inputedDate = sc.nextLine();
        }

        if (inputedDate != null && !inputedDate.isEmpty()) {
            if (isValidInputDate(inputedDate)) {
                String[] splittedDate = inputedDate.split("/");
                System.out.println("| A sua entrada foi: " + inputedDate);
                System.out.println("| E após a conversao temos: " + mapDate(splittedDate));
                System.out.println("| ---------------------------------------------------");
            } else {
                System.out.println("| A sua entrada foi: " + inputedDate);
                System.out.println("| Infelizmente este é uma data inválida. Talvez ano bissexto?");
            }
        } else {
            System.out.println("| Nada digitado :/ ");
        }
    }

    public static String mapDate(String[] splittedDate) {
        return splittedDate[0] + " de " + meses.get(splittedDate[1]) + " de " + splittedDate[2];
    }

    public static boolean isValidInputDate(String input) {

        boolean validState = false;
        validState = isFormatOk(input);

        if (validState) {
            String[] splittedDate = input.split("/");
            if (badYears.contains(splittedDate[2])) {
                validState = checkBadYear(splittedDate);
                return validState;
            }

            validState = checkBadDayMonth(splittedDate[0], splittedDate[1]);                
        }
        return validState;
    }

    public static boolean checkBadDayMonth(String day, String month){
        int bDay = Integer.valueOf(day);
        int bMonth = Integer.valueOf(month);
        List<Integer> months31 = new ArrayList<Integer>() {{
            add(1);add(3);add(5);add(7);
            add(8);add(10);add(12);
        }};

        if(months31.contains(bMonth)){
            return bDay > 0 && bDay < 32;
        }

        return bDay > 0 && bDay < 31;
    }

    public static boolean checkBadYear(String[] splittedDate) {
        boolean dayInRange = checkBadDay(splittedDate[0]);
        return badYears.contains(splittedDate[2]) && dayInRange && FEV.equals(splittedDate[1]);
    }

    public static boolean checkBadDay(String badDay) {
        int bDay = Integer.valueOf(badDay);
        return bDay > 0 && bDay < 30;
    }

    public static boolean isFormatOk(String input) {
        String dataFormat = "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)";
        return Pattern.matches(dataFormat, input);
    }

    public static void populateBadYears() {
        int firstYear = 1800;
        for (int i = 0; i < 1000; i += 4) {
            int badYear = firstYear + i;
            badYears.add(Integer.toString(badYear));
        }
    }
}