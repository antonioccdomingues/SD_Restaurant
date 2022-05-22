package serverSide.sharedRegions;

import genclass.GenericIO;
import commInfra.Message;
import serverSide.entities.*;;

public class BarMessageExchange
{
    private Bar bar;

    private boolean shutdown;

    public BarMessageExchange(Bar bar)
    {
        this.bar = bar;
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
            case 0:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setState((WaiterState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.saluteTheClient();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 1:
                chef = (Chef) Thread.currentThread();
                chef.setChefID((int) message.getStateFields()[0]);
                chef.setState((ChefState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.alertTheWaiter();
                state = new Object[]{chef.getChefID(),chef.getChefState()};
                break;
            case 2:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setState((WaiterState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.returningToTheBar();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 3:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setState((WaiterState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.prepareTheBill();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 4:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setState((WaiterState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                res = bar.lookAround();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 5:
                waiter = (Waiter) Thread.currentThread();
                waiter.setWaiterID((int) message.getStateFields()[0]);
                waiter.setState((WaiterState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.sayGoodbye();
                state = new Object[]{waiter.getWaiterID(),waiter.getWaiterState()};
                break;
            case 6:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.signalTheWaiter(student.getID());
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 7:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.callTheWaiter();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 8:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.enter();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 9:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.FirstStudent(student.getID());
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 10:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.exit();
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 11:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.shouldHaveArrivedEarlier(student.getID());
                state = new Object[]{student.getID(),student.getStudentState()};
                break;
            case 12:
                student = (Student) Thread.currentThread();
                student.setID((int) message.getStateFields()[0]);
                student.setState((StudentState) message.getStateFields()[1]);
                // Falta colocar a linha do logger
                bar.readTheMenu();
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