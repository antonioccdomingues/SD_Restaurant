package serverSide.objects;

import java.rmi.RemoteException;
import java.util.Objects;

import serverSide.main.Constants;
import clientSide.entities.WaiterState;
import clientSide.entities.StudentState;
import clientSide.entities.ChefState;
import genclass.GenericIO;
import genclass.TextFile;
import interfaces.GeneralReposInterface;


public class GeneralRepos implements GeneralReposInterface
{
    
    //name of logFile
    private final String logFileName;

    //state of the pilot
    private int chefState;

    //state of the waiter
    private int waiterState;

    //state of the students
    private int[] studentState;

    //Number of the course
    private int NCourse = 0;

    //number of a portion of a course
    private int NPortion = 0;

    //flag for students order
    private int orderID = -1;

    //array with seat order
    private String[] order = new String[Constants.students_number];
    private int orderFlag = 0;

    boolean match = false;

    /**
   *   Instantiation of a general repository object.
   *
   *     @param logFileName name of the logging file
   */

    public GeneralRepos (String logFileName)
    {
        for(int y =0; y< Constants.students_number; y++)
        {
            order[y] = "_";
        }
        if ((logFileName == null) || Objects.equals(logFileName, ""))
            this.logFileName = "logger";
        else this.logFileName = logFileName;

        waiterState = WaiterState.APPRAISING_SITUATION;
        chefState = ChefState.WAITING_FOR_AN_ORDER;
        studentState = new int[Constants.students_number];
        for (int i= 0; i < Constants.students_number;i ++)
            studentState[i] = StudentState.GOING_TO_THE_RESTAURANT;

        reportInitialStatus ();
        
    }

    /**
	 *   Set waiter state.
	 *
	 *     @param state waiter state
	 */

	public synchronized void setWaiterState (int state) throws RemoteException
    {
		waiterState = state;
		reportStatus ();
	}

    /**
	 *   Set student state.
	 *
	 *     @param id student id
	 *     @param state student state
	 */

    public synchronized void setStudentState (int id, int state) throws RemoteException
    {
        //System.out.println(state);
        if(state == StudentState.TAKING_A_SEAT_AT_THE_TABLE)
        {
            studentState[id] = state;
            setStudentsOrder(id);
        }
        // if(state == StudentState.PAYING_THE_BILL && pagar == 0)
        // {
        //     pagar =1;
        //     reportSpecificStatus("\nStudent: " + id + " Paying the Bill");
        // }
        // if (state == StudentState.GOING_HOME && sair == 0)
        // {
        //     reportSpecificStatus("\nLeaving the Restaurant");
        //     sair = 1;
        // }
        // if(state == StudentState.GOING_HOME)
        // {
            
        //     order[id] = "_";
        // }
        // if(state == StudentState.SELECTING_THE_COURSES && escolher == 0)
        // {
        //     reportSpecificStatus("\nStudent:" + id + " gathering individual plate choices");
        //     escolher =1;
        // }
        // if(state == StudentState.ENJOYING_THE_MEAL)
        // {
        //     reportSpecificStatus("\nStudent " + id + " eating");
        // }
        studentState[id] = state;
		reportStatus ();
        
        
    }

     /**
	 *   Set chef state.
	 *
	 *     @param state chef state
	 */

	public synchronized void setChefState (int state) throws RemoteException
    {
		chefState = state;
		reportStatus ();
	}

    /**
	 *   Set Ncourse number.
	 *
	 *     @param number number to add to Ncourse
	 */

	public synchronized void setNCourse (int number) throws RemoteException {NCourse += number;}

    /**
	 *   Set Nportion number.
	 *
	 *     @param number number to add to Ncourse
	 */

	public synchronized void setNPortion (int number) throws RemoteException
    {

         if(NCourse == 0 && NPortion == 0 && number == 1){
             NCourse++;
         }
         NPortion +=number;

         if(NPortion == 8)
         {
             NPortion = 1;
             NCourse++;
         }
    }

    /**
	 *   Set passenger state.
	 *
	 *     @param id passenger id
	 */

	public synchronized void setStudentsOrder (int id)  throws RemoteException
    {
		if (id != orderID)
        {
            String s=Integer.toString(id);  
            order[orderFlag] = s;
            orderFlag++;
            orderID = id; // isto secalhar nao funciona, tem de se verificar com todos os valores do array
            reportStatus ();
        }
		
	}

    private void reportInitialStatus ()
   {
      TextFile log = new TextFile ();                      // instantiation of a text file handler

      if (!log.openForWriting (".", logFileName))
         { GenericIO.writelnString ("The operation of creating the file " + logFileName + " failed!");
           System.exit (1);
         }
      log.writelnString ("                                          The Restaurant - Description of the internal state\n");
      log.writelnString ("Chef   Waiter  Stu0   Stu1   Stu2   Stu3   Stu4   Stu5   Stu6  NCourse  NPortion           Table");
      log.writelnString("State  State  State  State  State  State  State  State  State                     Seat0 Seat1 Seat2 Seat3 Seat4 Seat5 Seat6");
      if (!log.close ())
         { GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
           System.exit (1);
         }
      reportStatus ();
   }


   //WORK TO DO 
    private void reportStatus ()
    {
        TextFile log = new TextFile ();                      // instantiation of a text file handler

        String lineStatus = "";                              // state line to be printed

        if (!log.openForAppending (".", logFileName))
        {
            GenericIO.writelnString ("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit (1);
        }

        switch (chefState)
        { 
        case 0:     lineStatus += "WAFOR ";break;
        case 1:   lineStatus += "PRPCS ";break;
        case 2: lineStatus += "DSHPT ";break;
        case 3:      lineStatus += "DLVPT ";break;
        case 4:         lineStatus += "CLSSV ";break;
        }

        switch (waiterState)
        { 
        case 0:     lineStatus += " APPST ";break;
        case 1:   lineStatus += " PRSMN ";break;
        case 2:         lineStatus += " TKODR ";break;
        case 3:      lineStatus += " PCODR ";break;
        case 4:     lineStatus += " WTFPT ";break;
        case 5:   lineStatus += " PRCBL ";break;
        case 6:         lineStatus += " RECPM ";break;
        }

        for (int i = 0; i < Constants.students_number; i++)
            switch (studentState[i])
            { 
            case 0:  lineStatus += " GGTRT ";break;
            case 1:         lineStatus += " TKSTT ";break;
            case 2:        lineStatus += " SELCS ";break;
            case 3:   lineStatus += " OGODR ";break;
            case 4:  lineStatus += " CHTWC ";break;
            case 5:         lineStatus += " EJYML ";break;
            case 6:        lineStatus += " PYTBL ";break;
            case 7:   lineStatus += " GGHOM ";break;
            }

        lineStatus += String.format(" %4s     %4s ", NCourse, NPortion);//" " + inQueue + "    " + inFlight + "    " + inDestination;
        // FAZER AQUI A LINE STATUS PARA A ORDEM DE LUGARES DA TABLE!!
        // FAZER AQUI A LINE STATUS PARA A ORDEM DE LUGARES DA TABLE!!
        // FAZER AQUI A LINE STATUS PARA A ORDEM DE LUGARES DA TABLE!!

        lineStatus += String.format("    %4s  %4s  %4s  %4s  %4s  %4s  %4s  ", order[0], order[1], order[2], order[3], order[4], order[5], order[6]);

        log.writelnString (lineStatus);
        if (!log.close ())
        { 
            GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
            System.exit (1);
        }
    }

    /**
	 *   Write a specific state line at the end of the logging file, for example an message informing that
	 *   the plane has arrived.
	 *
	 *     @param message message to write in the logging file
	 */

	public synchronized void reportSpecificStatus (String message)  throws RemoteException
    {
		TextFile log = new TextFile ();                      // instantiation of a text file handler

		if (!log.openForAppending (".", logFileName)){
			GenericIO.writelnString ("The operation of opening for appending the file " + logFileName + " failed!");
			System.exit (1);
		}

		log.writelnString (message);
		if (!log.close ()){ 
			GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
			System.exit (1);
		}

	}

    public void shutdown() throws RemoteException
    {

    }
}


