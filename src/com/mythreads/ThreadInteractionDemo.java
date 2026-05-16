/**
 *  Java program to demonstrate methods wait(), notify().
 */

package com.mythreads;

import java.util.LinkedList;

/**
 *  ThreadInteractionDemo class.
 */
public class ThreadInteractionDemo {

    // JVM entry point.
    public static void main(String[] args) {

        // Creating a list to store objects..
        LinkedList sharedResource = new LinkedList<>();

        // Creating threads instances.
        Producer p = new Producer(sharedResource);
        Consumer c = new Consumer(sharedResource);

        // Starting threads.
        p.start();
        c.start();

    }
}

/**
 *  Producer class.
 */
class Producer extends Thread {

    // List to store objects.
    LinkedList sharedResource;

    // Constructor.
    Producer(LinkedList sharedResource) {
        super("Producer");
        this.sharedResource = sharedResource;
    }

    // Method to start thread.
    public void run() {

        // Iterating 5 times to access shared resource.
        for(int i = 0; i < 5; i++) {
            synchronized (sharedResource) {
                while (!sharedResource.isEmpty()) {
                    try {

                        // Printing information to console about access to sharedResource.
                        System.out.println("In the waiting mode in Producer is not empty.");

                        // Waiting fo the shared resource.
                        sharedResource.wait();

                    } catch (InterruptedException ex) {

                        ex.printStackTrace();

                    }
                }
                //Adding an element to the list.
                System.out.println("In Producer adding an item " + i);
                sharedResource.add(i);
                sharedResource.notify();
            }
        }
    }
}

/**
 *  Consumer class.
 */
class Consumer extends Thread {

    // List to store objects.
    LinkedList sharedResource;

    // Constructor.
    Consumer(LinkedList sharedResource) {
        super("Consumer");
        this.sharedResource = sharedResource;
    }

    // Method to start thread.
    public void run() {

        // Iterating 5 times to access shared resource.
        for (int i = 0; i < 5; i++) {
            synchronized (sharedResource) {
                while (sharedResource.isEmpty()) {
                    try {

                        // Printing information to console about access to sharedResource.
                        System.out.println("In the waiting mode in Consumer is empty.");

                        // Waiting fo the shared resource.
                        sharedResource.wait();


                    } catch (InterruptedException ex) {

                        ex.printStackTrace();

                    }
                }
                // Removing first element from the list element to sharedResource.
                System.out.println("Removing element.");
                sharedResource.remove();
                sharedResource.notify();
            }
        }
    }
}