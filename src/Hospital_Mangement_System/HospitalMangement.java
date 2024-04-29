package Hospital_Mangement_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import static java.lang.Class.forName;

public class HospitalMangement {
    private static final String url = "jdbc:mysql://localhost:3306/Hospital_Mangement_System";
    private static final String un = "root";
    private static final String pass = "91101@";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner s = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection(url, un, pass);
            patients p = new patients(con, s);
            doctor d = new doctor(con);

            while (true)
            {
                System.out.println("1).Add Patient");
                System.out.println("2).View Patients");
                System.out.println("3).View Doctors");
                System.out.println("4).Book Appointment");
                System.out.println("5).Exit");
                System.out.println("Enter your Choice");
                Scanner s1=new Scanner(System.in);
                int ch=s1.nextInt();
                if(ch==5){
                    break;
                }
                switch (ch)
                {
                    case 1:p.add_patient();
                    break;
                    case 2:p.view_patients();
                    break;
                    case 3:d.view_doctors();
                    break;
                    case 4:appoint(p,d,con,s);
                    break;
                    case 5:
                        System.out.println("Thank You For Using Hospital Mangement Software");
                        return;

                    default:
                        System.out.println("Enter Valid Option");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
public static void appoint(patients p,doctor d,Connection con,Scanner s)
{
    System.out.println("Enter the Patient Id");
    int pid=s.nextInt();
    System.out.println("Enter the Doctor Id");
    int did=s.nextInt();
    System.out.println("Enter the AppointMent date (yyyy-mm-dd):");
    String aptd=s.next();
    if(p.getpatientbyId(pid) && d.getdoctorbyId(did))
    {
        if(checkdoctoravailiable(did,aptd,con))
        {
            String aptq="insert into appointment (patient_id,doctor_id,appointment_date) values(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(aptq);
            ps.setInt(1, pid);
            ps.setInt(2, did);
            ps.setString(3, aptd);
            int ra = ps.executeUpdate();
            if (ra > 0) {
                System.out.println("Appointed Booked SucessFully");

            } else {
                System.out.println("Appointed Not Booked");
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        }
        else {
            System.out.println("Doctor Not availaible on this date");
        }
    }
    else
    {
        System.out.println("Either patient or doctor not exist");
    }

}
public static boolean checkdoctoravailiable(int did ,String aptd,Connection con)
{
String query ="select count(*) from appointment where doctor_id=? and appointment_date=?";
try {
PreparedStatement p=con.prepareStatement(query);
p.setInt(1,did);
p.setString(2,aptd);
ResultSet r=p.executeQuery();
if(r.next())
{
    int count=r.getInt(1);
    if(count==0)
    {
        return true;
    }
    else
    {
        return false;
    }
}
}
catch (Exception e)
{
    e.printStackTrace();
}
return  false;
}
}



