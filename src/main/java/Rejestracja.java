import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//@Data
//public class Rejestracja {
//    private List<Patient> patientsList = new ArrayList<>();
//
//    public Rejestracja(List<Patient> patientsList) {
//        this.patientsList = patientsList;
//    }
//
//    public void   isPatientOnList(String name){
//        if (patientsList.stream().anyMatch(Patient -> Patient.getName().equals(name) || Patient.getSurname().equals(name) || Patient.getPesel().equals(name)))
//            System.out.println("Pacjent jest w systemie");
//
//        else System.out.println("Pacjenta nie ma w systemie");
//
//    }
//}





import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigInteger;
import java.util.List;
@Data
@AllArgsConstructor
public class Rejestracja {
    private List<Patient> patientList;
    public boolean isRegistered(String pesel) {
        boolean        isRegistered = false;
        for (Patient patient : patientList) {
            if (patient.getPesel().equals(pesel)) {
                isRegistered = Boolean.TRUE;
            }
        }
        return isRegistered;
    }
    public boolean isRegistered(String name, String surname) {
        boolean isRegistered = false;
        int licznik = 0;
        for (Patient patient : patientList) {
            if (patient.getName().equals(name) && patient.getSurname().equals(surname)) {
                isRegistered = Boolean.TRUE;
                licznik ++;
            }
        }
        if (licznik>1){
            System.out.println("Jest więcej niż 1 pacjent o tym imieniu i nazwisku");
        }
        return isRegistered;
    }

    public Patient findPatientOrNull(String pesel) {
        Patient patientTemp = null;

        for (Patient patient : patientList) {
            if (patient.getPesel().equals(pesel)) {
                patientTemp = patient;
            }
        }

        return patientTemp;
    }
    public Patient findPatientOrNull(String name, String surname) {
        Patient patientTemp = null;

        for (Patient patient : patientList) {
            if (patient.getName().equals(name) && patient.getSurname().equals(surname)) {
                patientTemp = patient;
            }
        }
        return patientTemp;
    }
}
