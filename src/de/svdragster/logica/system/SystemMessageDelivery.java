package de.svdragster.logica.system;

import de.svdragster.logica.components.ComponentMailbox;
import de.svdragster.logica.components.meta.StdComponents;
import de.svdragster.logica.manager.Entity.Entity;
import de.svdragster.logica.manager.Entity.EntityManager;
import de.svdragster.logica.util.Pair;
import de.svdragster.logica.world.Engine;

import java.util.Observable;

/**
 * Created by Johannes Lüke on 10.12.2017.
 */

/**
 *  Does deliver Messages from one entity to another entity.
 */

public class SystemMessageDelivery extends System {

    private ComponentMailbox.Message CurrentProcessedMessage = null;

    public SystemMessageDelivery(){
        super.subscribe();
        this.setGlobalEntityContext(Engine.getInstance().getEntityManager());
    }

    @Override
    public void process(double delta) {
        //Because ANY entity could receive a message from another entity we check every entity if they
        //have mailboxes available.

        for(Entity e : getGlobalEntityContext().getEntityContext()) {
            Pair Mailbox = e.hasAssociationWith(StdComponents.MESSAGE);
            if((boolean)Mailbox.getFirst()) {
                while(((ComponentMailbox)Mailbox.getSecond()).hasOutGoingMail()) {
                    retrieveMessageFrom(((ComponentMailbox) Mailbox.getSecond()));
                    Pair check = CurrentProcessedMessage.To().hasAssociationWith(StdComponents.MESSAGE);
                    if ((boolean) check.getFirst())
                        this.sendMessage(CurrentProcessedMessage);
                    else{
                        //Recipent has no Mailbox
                        //throw new RuntimeException();
                    }
                }
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof ComponentMailbox.Message){
            sendMessage((ComponentMailbox.Message) o);
        }
    }

    private void retrieveMessageFrom(ComponentMailbox componentMailbox){
        CurrentProcessedMessage = componentMailbox.Outbox.poll();
    }

    void sendMessage(ComponentMailbox.Message msg){
        Pair checkDeliverability = msg.To().hasAssociationWith(StdComponents.MESSAGE);
        if((boolean)checkDeliverability.getFirst()) {
            ((ComponentMailbox)checkDeliverability.getSecond()).Inbox.add(msg);
        }
    }
}
