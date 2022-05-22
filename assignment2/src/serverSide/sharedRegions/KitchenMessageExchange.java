package serverSide.sharedRegions;

import genclass.GenericIO;
import commInfra.Message;
import serverSide.entities.*;;

public class KitchenMessageExchange
{
    private Kitchen kitchen;

    private boolean shutdown;

    public KitchenMessageExchange(Kitchen kitchen)
    {
        this.kitchen = kitchen;
        this.shutdown  = false;
    }

    public Message processAndReply(Message message)
    {
        Chef chef;
        Waiter waiter;
        Student student;

        Object res = null;
        Object[] state;

        switch(message.getOperation())
        {
            case 99:
                this.shutdown = true;
                message = null;
                state = new Object[]{};
                break;

            default:
                throw new IllegalArgumentException();
        }

        if (message != null)
        {
            message.setStateFields(state);
            message.setSizeStateFields(state.length);
            message.setReturnValue(res);
        }

        return message;
    }

    public boolean hasShutdown()
    {
        return this.shutdown;
    }
} 