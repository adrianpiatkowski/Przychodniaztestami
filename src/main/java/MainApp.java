import Wyjątki.TooManyPatientException;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

public class MainApp {

    private static Scanner scanner;
    private static Rejestracja patientService;
    private static List<Patient> patientList;


    public static void main(String[] args) throws TooManyPatientException {
        PobierzExcel apachePOIExcelRead = new PobierzExcel();
        patientList = apachePOIExcelRead.getPatientList();
        patientService = new Rejestracja(patientList);
        scanner = new Scanner(System.in);
        System.out.println("Wybierz akcje: \n0 - Zakończ działanie \n1 - Sprawdź czy pacjent jest zarejestrowany \n2 - Zarejestruj pacjenta \n3 - Usuń pacjenta \n4 - Przeprowadź test na korona wirusa");
        Integer action = scanner.nextInt();
        chooseTypeSearching(action);
    }

    private static void chooseTypeSearching(Integer typeNumber) throws TooManyPatientException {
        switch (typeNumber) {
            case 0:
                break;
            case 1:
                isRegistered();
                break;
            case 2:
                registerPatient();
                System.out.println("Udało się zarejestrować nowego pacjenta");
                System.out.println(patientList);
                break;
            case 3 :
                deletingPatient();
                System.out.println("Usunięto pacjenta");
                System.out.println(patientList);

            case 4 :
                testKoronawirus();
                System.out.println("Przeprowadzono test");
                System.out.println(patientList);
            default:
                break;
        }
    }


    //TODO Dopisać możliwość usunięcia z rejestru pacjenta oraz dodać pole do Pacjenta z ceną wizyty

    private static void deletingPatient() {
        System.out.println(patientList);
        Patient patientTemp = findPatient();
        patientList.remove(patientTemp);
        System.out.println(patientList);
        StwórzExcel.createExcel(patientList);
    }

    private static Patient findPatient() {
        System.out.println("Wyszukaj pacjenta po: \n0 - Zakończ działanie \n1 - imieniu i nazwisku \n2 - numerze PESEL");
        Integer action = scanner.nextInt();

        Patient patientTemp = null;

        switch (action) {
            case 0:
                break;
            case 1:
                System.out.println("Podaj imię: ");
                String name = scanner.next();
                System.out.println("Podaj nazwisko: ");
                String surname = scanner.next();
                patientTemp = patientService.findPatientOrNull(name, surname);
                break;
            case 2:
                System.out.println("Podaj pesel: ");
                String pesel = scanner.next();
                patientTemp = patientService.findPatientOrNull(pesel);
                break;
            default:
                break;
        }
        return patientTemp;
    }







    private static void registerPatient() throws TooManyPatientException {
        String name;
        String surname;
        String pesel;
        String cenaWizyty;
        String Koronawirus;
        System.out.println("Podaj imię:");
        name = scanner.next();
        System.out.println("Podaj nazwisko:");
        surname = scanner.next();
        System.out.println("Podaj pesel");
        pesel = scanner.next();
        System.out.println("Podaj kwotę wizyty: ");
        Double price = scanner.nextDouble();
        System.out.println("Czy ma wirusa: ");
       Koronawirus = scanner.next();

        if (patientService.isRegistered(pesel)){
            throw new TooManyPatientException();
        }

        Patient pacjent1 = new Patient(name,surname,pesel, price,Koronawirus);
        patientList.add(pacjent1);
        StwórzExcel.createExcel(patientList);

        //TODO dopisać rejestracje pacjenta
    }

    private static void isRegistered() {
        System.out.println("Sprawdź czy pacjent jest zarejestrowany po: \n0 - Zakończ działanie \n1 - imieniu i nazwisku \n2 - numerze PESEL");
        Integer action = scanner.nextInt();

        switch (action) {
            case 0:
                break;
            case 1:
                System.out.println("Podaj imię: ");
                String name = scanner.next();
                System.out.println("Podaj nazwisko: ");
                String surname = scanner.next();
                System.out.println(patientService.isRegistered(name, surname));


                break;

            //TODO W przypadku dopasowań więcej niż 1 rzucić użytkownikowi błąd
            case 2:
                System.out.println("Podaj pesel: ");
                String pesel = scanner.next();
                System.out.println(patientService.isRegistered(pesel));
                break;
            default:
                break;
        }
    }

    private static void testKoronawirus() {
        double koszt = 500;
        double walletPoBadaniu;
        int i = 0;
        List<Patient>bezBadania=new ArrayList<>();
        List<Patient>zBadaniem=new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getWallet() < koszt){
                bezBadania.add(patient);
                System.out.println("Pacjent o numerze na liście " + i + " ma za mało pieniędzy niech umiera");
            }
            if (patient.getWallet() > koszt) {
                zBadaniem.add(patient);
                walletPoBadaniu = patient.getWallet() - koszt;
                patient.setWallet(walletPoBadaniu);
                System.out.println("Pacjentowi o numerze na liście " + i + " badanie zostało wykonane");
            }
            i ++;

        }
        System.out.println("Lista osob bez badania:\n" + bezBadania);
        System.out.println("Lista osob z badaniem:\n" + zBadaniem);
        for (Patient patient:bezBadania
             ) {patient.setKoronawirus("BRAK BADANIA");

        }
        for (Patient patient:zBadaniem
             ) {
            Random random= new Random();
            Integer random1 = random.nextInt(2);
            System.out.println(random1);
            switch (random1){
                case 0:
                    patient.setKoronawirus("MA WIRUSA");
                    break;

                case 1:
                    patient.setKoronawirus("NIE MA WIRUSA");
                    break;
        }

        }
        StwórzExcel.createExcel(patientList);
    }
}