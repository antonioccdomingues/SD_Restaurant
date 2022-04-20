package sharedRegions;

import java.util.Objects;

import main.*;
import entities.*;
import genclass.GenericIO;
import genclass.TextFile;
import java.util.*;


public class GeneralRepos 
{
    
    //name of logFile
    private final String logFileName;

    //state of the pilot
    private ChefState chefState;

    //state of the waiter
    private WaiterState waiterState;

    //state of the students
    private StudentState[] studentState;

    //Number of the course
    private int NCourse = 0;

    //number of a portion of a course
    private int NPortion = 0;

    //flag for students order
    private int orderID = -1;

    //array with seat order
    private String[] order = new String[MainProgram.students_number];
    private int orderFlag = 0;

    boolean match = false;
    private int sair = 0;
    private int pagar = 0;
    private int escolher = 0;

    

    /**
   *   Instantiation of a general repository object.
   *
   *     @param logFileName name of the logging file
   */

    public GeneralRepos (String logFileName)
    {
        for(int y =0; y< MainProgram.students_number; y++)
        {
            order[y] = "_";
        }
        if ((logFileName == null) || Objects.equals(logFileName, ""))
            this.logFileName = "logger";
            else this.logFileName = logFileName;

        waiterState = WaiterState.APPRAISING_SITUATION;
        chefState = ChefState.WAITING_FOR_AN_ORDER;
        studentState = new StudentState[7];
        for (int i= 0; i < MainProgram.students_number;i ++)
            studentState[i] = StudentState.GOING_TO_THE_RESTAURANT;

        reportInitialStatus ();
        
    }

    /**
	 *   Set waiter state.
	 *
	 *     @param state waiter state
	 */

	public synchronized void setWaiterState (WaiterState state)
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

    public synchronized void setStudentState (int id, StudentState state)
    {
        //System.out.println(state);
        if(state == StudentState.TAKING_A_SEAT_AT_THE_TABLE)
        {
            studentState[id] = state;
            setStudentsOrder(id);
        }
        if(state == StudentState.PAYING_THE_BILL && pagar == 0)
        {
            pagar =1;
            reportSpecificStatus("\nStudent: " + id + " Paying the Bill");
        }
        if (state == StudentState.GOING_HOME && sair == 0)
        {
            reportSpecificStatus("\nLeaving the Restaurant");
            sair = 1;
        }
        if(state == StudentState.GOING_HOME)
        {
            
            order[id] = "_";
        }
        if(state == StudentState.SELECTING_THE_COURSES && escolher == 0)
        {
            reportSpecificStatus("\nStudent:" + id + " gathering individual plate choices");
            escolher =1;
        }
        if(state == StudentState.ENJOYING_THE_MEAL)
        {
            reportSpecificStatus("\nStudent " + id + " eating");
        }
        studentState[id] = state;
		reportStatus ();
        
        
    }

     /**
	 *   Set chef state.
	 *
	 *     @param state chef state
	 */

	public synchronized void setChefState (ChefState state)
    {
		chefState = state;
		reportStatus ();
	}

    /**
	 *   Set Ncourse number.
	 *
	 *     @param number number to add to Ncourse
	 */

	public synchronized void setNCourse (int number){NCourse += number;}

    /**
	 *   Set Nportion number.
	 *
	 *     @param number number to add to Ncourse
	 */

	public synchronized void setNPortion (int number)
    {

        if(NCourse == 0 && NPortion == 0 && number == 1){
            NCourse++;
            reportSpecificStatus("\nCourse:" + NCourse);
        }
        NPortion +=number;

        if(NPortion == 8)
        {
            NPortion = 1;
            NCourse++;
            reportSpecificStatus("\nCourse:" + NCourse);
        }
    }

    /**
	 *   Set passenger state.
	 *
	 *     @param id passenger id
	 */

	public synchronized void setStudentsOrder (int id){
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
      log.writelnString ("                                          The Restaurant - Description of the internal state");
      log.writelnString ("Chef   Waiter  Stu0   Stu1   Stu2   Stu3   Stu4   Stu5   Stu6 NCourse NPortion                Table");
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
        case WAITING_FOR_AN_ORDER:     lineStatus += "WAFOR ";break;
        case PREPARING_THE_COURSE:   lineStatus += "PRPCS ";break;
        case DISHING_THE_PORTIONS: lineStatus += "DSHPT ";break;
        case DELIVERING_THE_PORTIONS:      lineStatus += "DLVPT ";break;
        case CLOSING_SERVICE:         lineStatus += "CLSSV ";break;
        }

        switch (waiterState)
        { 
        case APPRAISING_SITUATION:     lineStatus += " APPST ";break;
        case PRESENTING_THE_MENU:   lineStatus += " PRSMN ";break;
        case TAKING_THE_ORDER:         lineStatus += " TKODR ";break;
        case PLACING_THE_ORDER:      lineStatus += " PCODR ";break;
        case WAITING_FOR_PORTION:     lineStatus += " WTFPT ";break;
        case PROCESSING_THE_BILL:   lineStatus += " PRCBL ";break;
        case RECEIVING_PAYMENT:         lineStatus += " RECPM ";break;
        }

        for (int i = 0; i < MainProgram.students_number; i++)
            switch (studentState[i])
            { 
            case GOING_TO_THE_RESTAURANT:  lineStatus += " GGTRT ";break;
            case TAKING_A_SEAT_AT_THE_TABLE:         lineStatus += " TKSTT ";break;
            case SELECTING_THE_COURSES:        lineStatus += " SELCS ";break;
            case ORGANIZING_THE_ORDER:   lineStatus += " OGODR ";break;
            case CHATTING_WITH_COMPANIONS:  lineStatus += " CHTWC ";break;
            case ENJOYING_THE_MEAL:         lineStatus += " EJYML ";break;
            case PAYING_THE_BILL:        lineStatus += " PYTBL ";break;
            case GOING_HOME:   lineStatus += " GGHOM ";break;
            }

        lineStatus += String.format(" %4s  %4s ", NCourse, NPortion);//" " + inQueue + "    " + inFlight + "    " + inDestination;
        // FAZER AQUI A LINE STATUS PARA A ORDEM DE LUGARES DA TABLE!!
        // FAZER AQUI A LINE STATUS PARA A ORDEM DE LUGARES DA TABLE!!
        // FAZER AQUI A LINE STATUS PARA A ORDEM DE LUGARES DA TABLE!!

        lineStatus += String.format("         %2s  %2s  %2s  %2s  %2s  %2s  %2s  ", order[0], order[1], order[2], order[3], order[4], order[5], order[6]);

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

	public synchronized void reportSpecificStatus (String message){
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
}


