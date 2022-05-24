package serverSide.sharedRegions;

import genclass.GenericIO;
import commInfra.Message;
import serverSide.entities.*;;

public class KitchenMessageExchange implements SharedRegionInterface
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

        Object res = null;
        Object[] state;

        switch(message.getOperation())
        {
            case 28:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                kitchen.startPreparation();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 29:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                kitchen.proceedToPresentation();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 30:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                kitchen.haveNextPortionReady();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 31:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                kitchen.continuePreparation();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 32:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                kitchen.cleanUp();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 33:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                res = kitchen.hasTheOrderBeenCompleted();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 34:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                res = kitchen.haveAllPortionsBeenDelivered();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 35:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                kitchen.handNoteToTheChef();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 36:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                kitchen.collectPortion();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 37:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setChefState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                kitchen.watchTheNews();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 38:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setWaiterState((int) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                res = kitchen.haveAllClientsBeenServed();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
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