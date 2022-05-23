package serverSide.sharedRegions;

import genclass.GenericIO;
import commInfra.Message;
import serverSide.entities.*;;

public class GeneralReposMessageExchange implements SharedRegionInterface
{
    private GeneralRepos generalRepos;

    private boolean shutdown;

    public GeneralReposMessageExchange(GeneralRepos generalRepos)
    {
        this.generalRepos = generalRepos;
        this.shutdown  = false;
    }

    public Message processAndReply(Message message)
    {
        Object res = null;
        Object[] state;

        switch(message.getOperation())
        {
            case 39:
			    GenericIO.writelnString ("-> setWaiterState");
			    generalRepos.setWaiterState((WaiterState) message.getParams()[0]);
			    break;
            case 40:
			    GenericIO.writelnString ("-> setStudentState");
			    generalRepos.setStudentState((int) message.getParams()[0],(StudentState) message.getParams()[1]);
			    break;
            case 41:
			    GenericIO.writelnString ("-> setChefState");
			    generalRepos.setChefState((ChefState) message.getParams()[0]);
			    break;
            case 42:
			    GenericIO.writelnString ("-> setNCourse");
			    generalRepos.setNCourse((int) message.getParams()[0]);
			    break;
            case 43:
			    GenericIO.writelnString ("-> setNPortion");
			    generalRepos.setNPortion((int) message.getParams()[0]);
			    break;
            case 44:
			    GenericIO.writelnString ("-> setStudentsOrder");
			    generalRepos.setStudentsOrder((int) message.getParams()[0]);
			    break;
            case 45:
			    GenericIO.writelnString ("-> reportInitialStatus");
			    // generalRepos.reportInitialStatus();
			    break;
            case 46:
			    GenericIO.writelnString ("-> reportStatus");
			    //generalRepos.reportStatus();
			    break;
            case 47:
			    GenericIO.writelnString ("-> reportSpecificStatus");
			    generalRepos.reportSpecificStatus((String) message.getParams()[0]);
			    break;

            case 99:
                this.shutdown = true;
                message = null;
                state = new Object[]{};
                break;
            default:
                throw new IllegalArgumentException();
        }
        return message;
    }

    public boolean hasShutdown()
    {
        return this.shutdown;
    }
} 