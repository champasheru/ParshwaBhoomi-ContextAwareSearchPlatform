package org.cs.parshwabhoomiapp.client.framework;


import java.lang.ref.WeakReference;

/**
 * Created by saurabhATchampasheruDOTbuild on 15/5/16.
 *
 * Abstract implementation of the Task interface.
 * Provides more of boiler-plate code, more on the framework side than app/domain.
 */
public abstract class Task implements ITask{
    private WeakReference<IClient> weakClient;
    private TaskEventListener taskEventListener;

    /**
     * Needs to be visible to the package & subclasses. This arrangement is needed because this class is a part of framework;
     * so only classes in the client & it's subpackages shall be able to access this constructor.
     * TODO: Check if we can have the PBClient implementation invoke this constructor using reflection.
     */
    final public void setClient(IClient iPrivateClient){
        weakClient = new WeakReference<IClient>(iPrivateClient);
    }

    final protected IClient getClient(){
        return weakClient.get();
    }

    final public TaskEventListener getTaskEventListener() {
        return taskEventListener;
    }

    public void execute(TaskEventListener taskEventListener){
        this.taskEventListener = taskEventListener;
        weakClient.get().submitTask(this);
    }

    protected abstract void executeInternal();
}
