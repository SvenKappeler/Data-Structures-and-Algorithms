package com.skappele;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class ExtensibleHashTable implements Serializable {
    
    // ATTRIBUTES ---------------------------------------------------------------            

    private int globalDepth;
    private int localDepth;
    private ArrayList<Block> directory;
    private String rootDirectoryPath;       // HashTable Output Directory Path            :: ie : ../HashTableOutput
    private String currentDirectoryPath;    // The directory for the Current Global Depth :: ie:  depth_1
    private String dirName; 
    private ArrayList<Block> oldDirectory;
    private int MaxLocalDepth = localDepth;

    // CONSTRUCTORS -------------------------------------------------------------
    
    public ExtensibleHashTable(int initialGlobalDepth, int initialLocalDepth , String rootDirectoryPath) {
        this.globalDepth = initialGlobalDepth;
        this.localDepth = initialLocalDepth;
        this.directory = new ArrayList<Block>(1 << this.globalDepth);
        this.rootDirectoryPath = rootDirectoryPath;
    }

    // GETTERS ------------------------------------------------------------------

    public int getGlobalDepth() {
        return globalDepth;
    }

    public int getLocalDepth() {
        return localDepth;
    }

    public ArrayList<Block> getDirectory() {
        return directory;
    }

    public String getRootDirectoryPath() {
        return rootDirectoryPath;
    }

    public String getCurrentDirectoryPath() {
        return currentDirectoryPath;
    }
    
    // SETTERS ------------------------------------------------------------------

    public void setGlobalDepth(int globalDepth) {
        this.globalDepth = globalDepth;
    }

    public void setLocalDepth(int localDepth) {
        this.localDepth = localDepth;
    }

    public void setDirectory(ArrayList<Block> directory) {
        this.directory = directory;
    }

    // METHODS ------------------------------------------------------------------
    
    // Makes a New Folder named after the current globalDepth

    public void newGlobalDepthFolder() {
        // Create directory with name as globalDepth
        dirName = String.valueOf(this.globalDepth);
        File directory = new File(rootDirectoryPath, "depth_" + dirName);
        boolean created = directory.mkdir();
    
        // Check if directory was created successfully
        if (created) {
            System.out.println("Directory created: " + directory.getPath());
            this.currentDirectoryPath = directory.getPath();
            System.out.println("Current Directory Path: " + this.currentDirectoryPath);
        } else {
            System.out.println("Failed to create directory: " + directory.getPath());
        }
    }

    public void newDirectoryFolders(){
        int numDirectories = 1 << getGlobalDepth();
        String formatString = "%" + getGlobalDepth() + "s";
        for (int i = 0; i < numDirectories; i++) {
            String directoryName = String.format(formatString, Integer.toBinaryString(i)).replace(' ', '0');
            File directory = new File(this.currentDirectoryPath + "/" + directoryName + "/");
            directory.mkdir();
        }
    }
    

    public String newBlock(String prefix, String localDepthBits) {
        // Create block with name as localDepth
        String blockName = String.valueOf(localDepthBits) + ".txt";
        File block = new File(this.currentDirectoryPath + "/" + prefix + "/", blockName);
    
        // Check if file already exists
        if (block.exists()) {
            System.out.println("Block already exists: " + block.getPath());
            System.out.println("This should never happen");
            return block.getPath();
        }
    
        try {
            boolean created = block.createNewFile();
            // Check if file was created successfully
            if (created) {
                System.out.println("Block created: " + block.getPath());
                return block.getPath();
                
            } else {
                System.out.println("Failed to create block: " + block.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return block.getPath();
    }

    

    public void insert(Business business) {

        System.out.println(" ");
        System.out.println("::::::::::: INSERTING :::::::::::");
        System.out.println(business.getBusinessId());
    

        // Determine which PreFix and Block to insert into
        String bitString = business.getBitString();    
        // Add bits to from of bitString to make it the same length as globalDepth + globalDepth
        int bitStringLength = bitString.length();
        while (bitString.length() < this.globalDepth + this.globalDepth + bitStringLength) {
            bitString = "0" + bitString;
        }
    
        // Example bitString: 0000000011 0100 10 => 01 Prefix, 0100 Block


        // LOOKING FOR THE BLOCK TO INSERT INTO PHYSLICALLY EXISTING
    
        Block block = null;
        Block targetBlock = null;
        
        String prefixBits = bitString.substring(bitString.length() - this.globalDepth, bitString.length());

        //System.out.println(bitString + " " + blockBits + " " + prefixBits);

        for( int i = globalDepth; i > 0; i--){
            String blockBits = bitString.substring(bitString.length() - this.globalDepth - i, bitString.length() - this.globalDepth);
            
            // Get the block for the given prefix
            targetBlock = new Block(i, 1 << i, blockBits, prefixBits);

            // Search the directory for the block with the same prefix and local depth
            //System.out.println("BlockBits: " + targetBlock.getBlockBits() + " Prefix: " + targetBlock.getPrefix());
            for (Block b : directory) {
                //System.out.println("BlockBits: " + b.getBlockBits() + " Prefix: " + b.getPrefix());
                if (b.getPrefix().equals(targetBlock.getPrefix()) && b.getBlockBits().equals(targetBlock.getBlockBits())) {
                    block = b;
                    i = 0;
                    break;
                }
            }
        }
        



        int index = directory.indexOf(block);
   
        // CASE 1: Block is non-existent
        if (block == null) {
            String blockBits = bitString.substring(bitString.length() - this.globalDepth - 1, bitString.length() - this.globalDepth);
            targetBlock = new Block(1, 1 << 1, blockBits, prefixBits);
            // Create new block (Physical File)
            targetBlock.setFilePath(newBlock(prefixBits, blockBits));
            // Create new block (Logical)
            targetBlock.setPrefix(prefixBits);
            targetBlock.setBlockBits(blockBits);

            targetBlock.insert(business.getBusinessId(), business);
            directory.add(targetBlock);
            try {
                targetBlock.saveBusinessesToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Business inserted successfully.");
            //this.printDirectory();
            return;
        }
    

        // CASE 2: Block is not full
        if ((block.insert(business.getBusinessId(), business)) == true) {
            try {
                block.saveBusinessesToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Business inserted successfully.");
        } else {

            // CASE 2.1: Block is full and localDepth < globalDepth
            if(block.isFull() && block.getLocalDepth() < globalDepth){
                System.out.println("Block is full and localDepth < globalDepth");
                System.out.println(block.localDepth + "<" + globalDepth);
                // Split the block
                splitBlock(index);
                // Insert into the new block
                insert(business);
            }

            // CASE 2.2: Block is full and localDepth == globalDepth
            else{
                System.out.println("Block is full and localDepth == globalDepth");
                // Double the directory
                rehash();
                // Insert into the new block
                insert(business);
            }
        }
    }
    

    private void splitBlock(int blockDirectory) {
        Block oldBlock = this.directory.get(blockDirectory);
        System.out.println("Splitting Block: " + oldBlock.getBlockBits() + " " + oldBlock.getPrefix());

        // Create 2 Blocks
        Block newBlock0 = new Block(oldBlock.localDepth + 1, 1 << (this.localDepth + 1), "0" + oldBlock.getBlockBits(), oldBlock.getPrefix());
        Block newBlock1 = new Block(oldBlock.localDepth + 1, 1 << (this.localDepth + 1), "1" + oldBlock.getBlockBits(), oldBlock.getPrefix());

        newBlock0.setBlockBits("0" + oldBlock.getBlockBits());
        newBlock1.setBlockBits("1" + oldBlock.getBlockBits());



        if(localDepth < MaxLocalDepth){
            this.MaxLocalDepth = localDepth;
        }

        // Loop through the old block and rehash the businesses
        for (int i = 0; i < oldBlock.getBusinesses().length; i++) {
            Business business = oldBlock.getBusinesses()[i].getValue();
            String bitString = business.getBitString();
            int bitStringLength = bitString.length();
            while (bitString.length() < this.globalDepth + this.localDepth + bitStringLength) {
                bitString = "0" + bitString;
            }
            String blockBits = bitString.substring(bitString.length() - this.globalDepth - this.localDepth, bitString.length() - this.globalDepth);


            if (blockBits.equals("0" + oldBlock.getBlockBits())) {
                newBlock0.insert(business.getBusinessId(), business);
            } else {
                newBlock1.insert(business.getBusinessId(), business);
            }
        }


        File oldBlockFile = new File(oldBlock.getFilePath());
        if (oldBlockFile.canWrite()) {
            if (!oldBlockFile.delete()) {
                System.out.println("Failed to delete old block file");
            }
        } else {
            System.out.println("File is not writable");
        }

        // Add new blocks to directory
        this.directory.add(newBlock0);
        this.directory.add(newBlock1);

        // Save new blocks to file
        newBlock0.setFilePath(newBlock(newBlock0.getPrefix(), newBlock0.getBlockBits()));
        newBlock1.setFilePath(newBlock(newBlock1.getPrefix(), newBlock1.getBlockBits()));

        try {
            newBlock0.saveBusinessesToFile();
            newBlock1.saveBusinessesToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(":::: DELETE OLD BLOCK ::::");

        // Remove old block from directory
        this.directory.remove(blockDirectory);

        System.out.println(" ");
    }

    private void rehash() {
        System.out.println(":::: REHASH ::::");
        System.out.println(" ");

        this.oldDirectory = this.directory;
        this.directory = new ArrayList<>(1 << globalDepth);        
        this.globalDepth++;

        newGlobalDepthFolder();
        newDirectoryFolders();

        // For every business in the old directory insert into the new directory
        for (Block block : this.oldDirectory) {
            for (int i = 0; i < block.getBusinesses().length && block.getBusinesses()[i] != null; i++) {
                Business business = block.getBusinesses()[i].getValue();
                try {
                    block.saveBusinessesToFile();
                    insert(business);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void printDirectory() {
        System.out.println("Global Depth: " + this.globalDepth);
        System.out.println("Local Depth: " + this.localDepth);
        System.out.println("Directory: ");
        for (Block block : this.directory) {
            System.out.println(block.getBlockBits() + block.getPrefix());
        }
    }

    // A method to get a business from the hash table
    
    public Business getBusiness(String businessId) {

        Block targetBlock = null;
        Block block = null;

        String bitString = getBitString(businessId);
        System.out.println("BitString: " + bitString);

        String prefixBits = bitString.substring(bitString.length() - this.globalDepth, bitString.length());

        // Get the bit string for the business
        for( int i = globalDepth; i > 0; i--){
            String blockBits = bitString.substring(bitString.length() - this.globalDepth - i, bitString.length() - this.globalDepth);
            
            // Get the block for the given prefix
            targetBlock = new Block(i, 1 << i, blockBits, prefixBits);

            // Search the directory for the block with the same prefix and local depth
            //System.out.println("BlockBits: " + targetBlock.getBlockBits() + " Prefix: " + targetBlock.getPrefix());
            for (Block b : directory) {
                //System.out.println("BlockBits: " + b.getBlockBits() + " Prefix: " + b.getPrefix());
                if (b.getPrefix().equals(targetBlock.getPrefix()) && b.getBlockBits().equals(targetBlock.getBlockBits())) {
                    block = b;
                    i = 0;
                    break;
                }
            }
            if(block != null){
                System.out.println("Block Found: " + block.getBlockBits() + " " + block.getPrefix());
                return block.getBusiness(businessId);
            }
        }
        return null;
    }

    // A method to get the bit string for a businessId inside of this class
    private String getBitString(String businessId) {
        int hash = businessId.hashCode();

        // Convert the integer to a 32-bit binary string
        String bitString = Integer.toBinaryString(hash);
        int bitStringLength = bitString.length();

        // Pad the binary string with leading zeros
        while (bitString.length() < this.globalDepth + this.globalDepth + bitStringLength) {
            bitString = "0" + bitString;
        }
        return bitString;
    }

    // A method to extract all of the businesses from the hash table and store them in an ArrayList to pass to the clustering algo
    public ArrayList<Business> getAllBusinesses() {
        ArrayList<Business> businesses = new ArrayList<>();
        for (Block block : this.directory) {
            for (int i = 0; i < block.getBusinesses().length && block.getBusinesses()[i] != null; i++) {
                businesses.add(block.getBusinesses()[i].getValue());
            }
        }
        return businesses;
    }

    // CLUSTERING METHODS -----------------------------------------------------------------------------------------------

    // A method for taking the businesses from the clusters
    // Making a new directory based on cluster name, and copying 
    // the businesses from the old directory to the new directory

    public void saveClusters(ArrayList<Cluster> clusters) {

        // Save the old directory
        oldDirectory = this.directory;

        // For each cluster
        for (Cluster cluster : clusters) {
            // Create a new directory
            newGlobalDepthFolderWithClusters(cluster);
            newDirectoryFolders();

            // For each business in the cluster look for it in the old directory
            for (Business business : cluster.getBusinesses()) {
                // Get the file path for the business
                String businessFilePath = business.getFilePath();

            }
        }
    }

    public void newGlobalDepthFolderWithClusters(Cluster cluster) {
        // Create directory with name as globalDepth
        dirName = String.valueOf(this.globalDepth);
        File directory = new File(rootDirectoryPath, "depth_" + dirName + "_" + cluster.getClusterName());
        boolean created = directory.mkdir();
    
        // Check if directory was created successfully
        if (created) {
            System.out.println("Directory created: " + directory.getPath());
            this.currentDirectoryPath = directory.getPath();
            System.out.println("Current Directory Path: " + this.currentDirectoryPath);
        } else {
            System.out.println("Failed to create directory: " + directory.getPath());
        }
    }

}
