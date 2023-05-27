package com.skappele;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Block implements Serializable{

    // ATTRIBUTES ---------------------------------------------------------------

    int localDepth;
    int capacity;
    int size;
    Pair<String, Business>[] businesses;
    String blockBits;
    String filePath;
    String prefixBits;

    // CONSTRUCTORS -------------------------------------------------------------

    @SuppressWarnings("unchecked")    
    public Block(int localDepth, int capacity, String blockBits, String prefixBits) {
        this.localDepth = localDepth;
        this.capacity = (1 << localDepth);
        this.size = 0;
        this.businesses = new Pair[this.capacity];
        this.blockBits = blockBits;
        this.prefixBits = prefixBits;
    }

    // GETTERS ------------------------------------------------------------------

    public int getLocalDepth() {
        return localDepth;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public Pair<String, Business>[] getBusinesses() {
        return businesses;
    }

    public String getBlockBits() {
        return blockBits;
    }

    public String getPrefix() {
        return prefixBits;
    }

    public String getFilePath() {
        return filePath;
    }

    // SETTERS ------------------------------------------------------------------

    public void setLocalDepth(int localDepth) {
        this.localDepth = localDepth;
    }

    public void increaseSize() {
        this.size++;
    }

    // May need to be expanded
    public void increaseLocalDepth() {
        this.localDepth++;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public void setPrefix(String prefixBits) {
        this.prefixBits = prefixBits;
    }

    public void setBlockBits(String blockBits) {
        this.blockBits = blockBits;
    }

    // METHODS ------------------------------------------------------------------

    public boolean isFull() {
        return this.size == this.capacity;
    }

    public boolean isNull() {
        return this.size == -1;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean insert(String businessId, Business business){
        if ((this.isFull() || this.isNull()) ) {
            return false;
        }
        this.businesses[this.size] = new Pair<>(businessId, business);
        this.increaseSize();
        return true;
    }

    public void saveBusinessesToFile() throws IOException {
        // Loop through all businesses in the block and setFilePath
        for (int i = 0; i < this.size; i++) {
            this.businesses[i].getValue().setFilePath(this.filePath);
        }
        // Save the businesses to the file
        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(this.businesses);
        objectOut.close();
        fileOut.close();
    }

    public void printBusinesses() {
        for (int i = 0; i < this.size; i++) {
            System.out.println(this.businesses[i].getKey() + " " + this.businesses[i].getValue().getName());
        }
    }

    public Business getBusiness(String businessId) {
        for (int i = 0; i < this.size; i++) {
            if (this.businesses[i].getKey().equals(businessId)) {
                return this.businesses[i].getValue();
            }
        }
        return null;
    }
}
    

