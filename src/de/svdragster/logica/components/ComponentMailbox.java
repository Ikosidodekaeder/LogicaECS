package de.svdragster.logica.components;

import de.svdragster.logica.components.meta.StdComponents;
import de.svdragster.logica.util.Delegate;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by Johannes Lüke on 10.12.2017.
 */

public class ComponentMailbox extends Component {

    public static class Message{
        private int          Recipient;
        private int          Origin;
        private String       Text;

        private boolean      invokable = true;
        private Delegate     Callback;




        public Message(int Recipient, int Origin, String text, Delegate callback){
            this.Recipient = Recipient;
            this.Origin = Origin;

            this.Text = text;
            this.Callback = callback;
        }

        public void InvokeCallback(Object...args){
            if(invokable)
                Callback.invoke(args);
        }

        public int From(){
            return Origin;
        }

        public int To(){
            return Recipient;
        }

        public String toString(){
            return Text;
        }
    }

    public Queue<Message> Inbox;
    public Queue<Message> Outbox;

    public ComponentMailbox(){
        super.setType(StdComponents.MESSAGE);
        Inbox = new LinkedList<Message>();
        Outbox = new LinkedList<Message>();
    }


    public boolean hasOutGoingMail(){
        return !Inbox.isEmpty();
    }




}
