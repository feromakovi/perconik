### Overview
This is a fork of official [PerConIK Eclipse Integration project](http://perconik.github.io/) because it requires some of PerConIK features. This is implementation of diploma thesis for evaluating programmer's knowledges based on participating on software development project. The analysator is implemented as extension to Eclipse IDE.

### Requirements
- Java Platform SE 7 (or higher)
- Java projects versioned in Git
- Eclipse IDE

### Installation
Quick Installation Guide:

1. Eclipse → Help → Install New Software... → Available Software Sites → Add
	
	 Name: Analysator
	 
	 Location: [http://feromakovi.github.io/perconik/update](http://feromakovi.github.io/perconik/update)
2. Restart Eclipse
3. Download compressed [file with neccessary models](https://dl.dropboxusercontent.com/u/13095494/models.zip)
4. Unzip models to folder with writable permission

### Usage
When you have extension installed switch to `Package Explorer` view. If it's not shown, you can navigate:

- Eclipse → Window → Show View → Other → General → Package Explorer
- import Java project
- right click on the project and run `Assign Authors`
- when the file picker appears, navigate to folder with models and pick the `log.txt` file
- wait until message box with text `Hotovo` is not displayed

When it is all over the extension should create some new folders in model's folder. There are some metadata of programmer's knowledges retrieved from project where they have participated.

Folders `technologies`, `familiarity`, `complexities` and picked file `log.txt` please compress to ZIP file and send me (@feromakovi) to feromakovi@gmail.com. 