import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient("Jakub", "Dąbrowski","99087666341", 350,"brak"));
        patientList.add(new Patient("Mikołaj", "Romanowski", "12345678910", 1530,"brak"));
        patientList.add(new Patient("Jan", "Kowalski", "82345678910", 1670,"brak"));
        StwórzExcel apachePOIExcelWrite = new StwórzExcel();
        apachePOIExcelWrite.createExcel(patientList);
        Rejestracja patientService = new Rejestracja(patientList);
        System.out.println(patientService.isRegistered("99087666341"));
        System.out.println(patientService.isRegistered("123"));
        System.out.println(patientService.isRegistered("Asd", "Asd"));
        System.out.println(patientService.isRegistered("Jakub", "Dąbrowski"));
        System.out.println("-------------------------------");
        Patient patient = new Patient("Test", "Kowalski", "82345678910", 100000,"brak");
        System.out.println(patientService.isRegistered("Test", "Kowalski"));
        patientList.add(patient);
        apachePOIExcelWrite.createExcel(patientList);
        System.out.println(patientService.isRegistered("Test", "Kowalski"));
        System.out.println(patientList);
    }
}