package de.svdragster.logica.manager.Entity;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import de.svdragster.logica.util.Pair;
import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.meta.ComponentType;
import de.svdragster.logica.world.Engine;

import static de.svdragster.logica.util.Pair.of;

/**
 * Created by Johannes on 24.01.2018.
 */

public class Entity {

    static class Signature{
        public static String          calculateSignature(String Source){
            byte[]          ComponentSignature;
            MessageDigest digest = null;

            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            ComponentSignature = digest.digest(
                    Source.getBytes(StandardCharsets.UTF_8)
            );

            //System.out.println(convertSignatureToHex(ComponentSignature));
            return convertSignatureToHex(ComponentSignature);
        }

        private static String convertSignatureToHex(byte[] hash){
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
    }

    private List<Component> associatedComponents = new ArrayList<>();

    public String getComponentSignature() {
        return ComponentSignature;
    }

    private String ComponentSignature = new String();

    private String TypesToString(){
        String tmp = new String();
        for(Component c : associatedComponents)
            tmp += c.getType().toString();
        return tmp;
    }

    public String generateSignature(){
        return  Signature.calculateSignature(TypesToString());
    }

    public Entity(){};
    public Entity(Component... components){
        for(Component c : components){
            associateComponent(c);
            Engine.getInstance().getComponentManager().add(c);
        }




    }

    public List<Component> getAssociatedComponents(){
        return associatedComponents;
    }

    /**
     * Simply checks if the current getEntity has any number of Components associated with it.
     * @return Boolean
     */
    public boolean hasAnyAssociations() { return !associatedComponents.isEmpty();}

    /**
     * Checks if a certain type of Component is associated with the getEntity. If this is the case
     * the returned Pair contains two Values. The first on indicates the positive found a a type the
     * second parameter holds a non-null reference to the component you just checked.
     * If the type you try to check for is not found to be associated with the entity the first paramter
     * indicates this by the boolean value of "false" and the second paramter should not be touched
     * at all else you will find a null-reference.
     *
     *
     * @param type Component Type which is going to be looked up
     * @param <BOOL_HasComponent> boolean type. If true the second param contains a non null ref to a Component
     * @param <ComponentReference> Reference to a Component associated with the current getEntity
     * @return
     */
    public Pair hasAssociationWith(ComponentType type){

        for( Component c : associatedComponents)
            if( c.getType() == type)
                return of(true,c);
        return of(false,null);
    }

    /**
     * Removes a associated component from the getEntity. If successful it returns true otherwise false;
     * if false this indicates that the component was never associated with the entity in the first
     * plase
     * @param type
     * @return true on success
     */
    public boolean removeAssociation(ComponentType type){
        Pair<Boolean,Component> ret = hasAssociationWith(type);

        if(ret.getFirst() == true){
            ret.getSecond().setBackAssociation(null);
            associatedComponents.remove(ret.getSecond());
            Engine.getInstance().getComponentManager().remove(ret.getSecond());
            return ret.getFirst();
        }

        ComponentSignature = generateSignature();
        return ret.getFirst();
    }

    /**
     * associate a new component to a entity. If the entity already contains the same type
     * of component this component will be removed and replaced by the new one of the same type.
     * @param component
     */
    public void associateComponent(Component component){
        Pair<Boolean,Component> ret = hasAssociationWith(component.getType());
        if(ret.getFirst() == false) {
            component.setBackAssociation(this);
            associatedComponents.add(component);
            Engine.getInstance().getComponentManager().add(component);
        }else{
            removeAssociation(ret.getSecond().getType());
            associateComponent(component);
            Engine.getInstance().getComponentManager().add(component);
        }

        ComponentSignature = generateSignature();
    }

    /**
     * Any getEntity equals another entity if they have the same component signature.
     * If by chance you compare an entity to it self we do not need to check the
     * signature!
     *
     * @param o
     * @return true on same signature
     */
    @Override
    public boolean equals(@NotNull Object o) {
        if(o == null)
            return false;
        if(o == this)
            return true;

        if(o instanceof Entity){
            Entity e = (Entity) o;
            //Compares the hashes and does not loop
            if(this.getComponentSignature().equals(e.getComponentSignature()))
                return true;

            /*
            if(this.getAssociatedComponents().size() != e.getAssociatedComponents().size())
                return false;

            for(int i = 0; i < this.getAssociatedComponents().size(); i++)
                if(associatedComponents.get(i).getType() != e.getAssociatedComponents().get(i).getType())
                    return false;

            return true;
            */
        }

        return false;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Component component: associatedComponents)
            builder.append(component.toString() + ", ");
        return builder.toString();
    }
}
