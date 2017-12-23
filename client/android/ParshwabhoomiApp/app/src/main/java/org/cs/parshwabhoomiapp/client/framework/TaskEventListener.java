package org.cs.parshwabhoomiapp.client.framework;

/**
 * Created by saurabhATchampasheruDOTbuild on 5/13/2016.
 *
 * The consumer of the Client shall first get a task specific to a particular purpose.
 * The task returned thereby can be executed by providing the callback implementation as
 * specified by this interface. Whenever the task has some event to convey to it's consumer,
 * it will be done using this callback.
 * The events can be - completion of an operation, update to an operation (think of file upload/download) etc.
 */
public interface TaskEventListener {
    public void onFinish(Object result, Exception exception);
}
