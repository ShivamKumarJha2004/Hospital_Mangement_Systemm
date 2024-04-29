package Hospital_Mangement_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class doctor {
    private Connection con;
    //private Scanner s;
    doctor(Connection c)
    {
        this.con=c;

    }

    public void view_doctors()
    {
        String query="select * from doctors";
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ResultSet r=ps.executeQuery();
            System.out.println("Doctor_Details");
            System.out.println("+-----+-------------------+--------------------+");
            System.out.println("| Id  |  Name             |   Specialization   |      " );
            System.out.println("+-----+-------------------+--------------------+");
            while(r.next())
            {
                int id=r.getInt("doc_id");
                String n=r.getString("doc_name");
                String sp=r.getString("specialization");
                System.out.printf("| %-4s| %-18s| %-19s|\n",id,n,sp);
                System.out.println("+-----+-------------------+--------------------+");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public boolean getdoctorbyId(int id)
    {
        String query="select * from doctors where doc_id=?";
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

