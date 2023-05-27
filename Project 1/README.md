# Project 1

Project 1 entails the creation of a custom hashtable designed specifically for handling Yelp's JSON datasets.

## Creation of Business Objects

The program begins by creating 10,000 business objects based on the businesses.json file. These objects hold relevant information necessary for the subsequent steps.

## Review Attachment

The next phase involves iterating over the reviews.json file and attaching reviews to their respective business objects. This is accomplished by matching the reviews with businesses using the unique business ID.

## Hashtable

The newly populated business objects are stored in a custom-built hashtable. This hashtable is designed to dynamically resize as necessary, ensuring efficient storage and retrieval of the data.

## GUI

Once the hashtable is fully populated with the business objects, a GUI is launched. This GUI, powered by various APIs, takes user input in the form of an address and returns the three businesses that are most similar to the given address. The criteria for determining similarity is a combination of the physical proximity of the business to the entered address and the presence of similar keywords in the reviews associated with the business.

