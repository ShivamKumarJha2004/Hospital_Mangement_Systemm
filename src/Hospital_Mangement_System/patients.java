package Hospital_Mangement_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class patients {

    private Connection con;
    private Scanner s;
patients(Connection c, Scanner sa)
{
this.con=c;
this.s=sa;
}
public  void add_patient()
{
    System.out.println("Enter Patient Name");
    String name=s.next();
    System.out.println("Enter the Patient Age");
    int age=s.nextInt();
    System.out.println("Enter the Patient Gender");
    String gen=s.next();

    try{
     String query="insert into patients(pat_name,pat_age,pat_gender)values(?,?,?)";
     PreparedStatement ps=con.prepareStatement(query);
     ps.setString(1,name);
     ps.setInt(2,age);
     ps.setString(3,gen);
     int ra= ps.executeUpdate();
     if(ra>0)

     {
         System.out.println("Patient Added Successfully !");
     }
     else {
         System.out.println("Failed To add Patients !");
     }

    }
    catch (Exception e)
    {
       e.printStackTrace();
    }

}
public void view_patients()
{
    String query="select * from patients";
    try{
       PreparedStatement ps=con.prepareStatement(query);
        ResultSet r=ps.executeQuery();
        System.out.println("Patient_Details");
        System.out.println("+-----+-------------------+--------+-----------+");
        System.out.println("| Id  |  Name             | Age    | Gender    |" );
        System.out.println("+-----+-------------------+--------+-----------+");
        while(r.next())
        {
            int id=r.getInt("pat_id");
            String n=r.getString("pat_name");
            int age=r.getInt("pat_age");
            String gen=r.getString("pat_gender");
            System.out.printf("| %-4s| %-18s| %-7s| %-10s|\n",id,n,age,gen);
            System.out.println("+-----+-------------------+--------+-----------+");
        }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
}
public boolean getpatientbyId(int id)
{
    String query="select * from patients where pat_id=?";
    try {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet r=ps.executeQuery();
        if(r.next())
        {
            return true;
        }

    }
    catch (Exception e)

    {
      e.printStackTrace();
    }
return  false;
}
}
