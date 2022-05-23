package serverSide.sharedRegions;

import genclass.GenericIO;
import commInfra.Message;
import serverSide.entities.*;;

public class TableMessageExchange implements SharedRegionInterface
{
    private Table table;

    private boolean shutdown;

    public TableMessageExchange(Table table)
    {
        this.table = table;
        this.shutdown  = false;
    }

    public Message processAndReply(Message message)
    {
        Waiter waiter;
        Student student;

        Object res = null;
        Object[] state;

        switch(message.getOperation())
        {
            case 13:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setState((WaiterState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.getThePad();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 14:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setState((WaiterState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.deliverPortion();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 15:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setState((WaiterState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                res = table.haveAllClientsBeenServed();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 16:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setState((WaiterState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.presentTheBill();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 17:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.informCompanion();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 18:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.prepareTheOrder();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 19:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.joinTheTalk();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 20:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.hasEverbodyFinished();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 21:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.startEating();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 22:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.endEating();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 23:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.honourTheBill();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 24:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.addUpOnesChoice();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 25:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                res = table.hasEverybodyChosen();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 26:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.describeTheOrder();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 27:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                table.waitingToBeServed(student.getID());
                state = new Object[]{student.getID(),student.getStudentState()};
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