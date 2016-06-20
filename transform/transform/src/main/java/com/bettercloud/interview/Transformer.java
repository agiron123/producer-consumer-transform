package com.bettercloud.interview;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Transformer implements ITransformer {
    private ITransformModel transformModel;

    public Transformer(ITransformModel transformModel) {
        this.transformModel = transformModel;
    }
    public void transform(JsonNode rootNode) {
        //TODO: Make a shhared storage package and have a ConcurrentHashMapStorage implementation.
        //Also have an IStorage interface, in a writeup, mention that we will be able to store things in our model regardless
        //of the means of storage if we do things this way.
        //Traverse through the nodes in the JSON object and add them to the model.

        Iterator elements = rootNode.elements();
        while(elements.hasNext()) {

            JsonNode current = elements.next();
            //TODO: Do stuff with the JSON Node here.

        }

        //transformModel.addEntry(email)

    }

}