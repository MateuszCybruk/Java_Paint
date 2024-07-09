----------------------------------Project Description----------------------------------

This project is a simple graphical editor created using Java Swing. 
It features a menu bar, status bar, and drawing area, allowing users to create 
and manipulate shapes like circles and squares, draw freehand with a pen, 
and manage the file contents of their drawings.

----------------------------------Features----------------------------------

● Menu Bar: Provides options for file operations, drawing shapes, color selection, and clearing the drawing area.

● File Menu:
 - Open: Opens a file using JFileChooser, displays the saved shapes, and allows further drawing/modification.
 - Save: Saves the current drawing to the current file. If no file exists, prompts the user to select a file using JFileChooser.
 - Save As: Saves the current drawing to a user-specified file using JFileChooser.
 - Quit: Exits the editor, checking if there are unsaved changes.

● Draw Menu:
- Figure:
  - Circle: Draws a circle at the mouse cursor position with a fixed radius and random color.
  - Square: Draws a square at the mouse cursor position with a fixed side length and random color.
  - Pen: Allows freehand drawing by dragging the mouse cursor.
  - Color: Sets the color for the pen mode using JColorChooser.
  - Clear: Clears the drawing area.

● Status Bar: 
 Displays the current drawing mode (Circle, Square, or Pen) and the file state (New, Modified, or Saved).

● Shape Deletion: 
 Deletes shapes (circles or squares) when the mouse is clicked within the shape boundary while pressing the 'D' key. The deletion is confirmed by a dialog box.

● Window Title: 
 Displays the name of the edited file or "Simple Draw" if no file is loaded.

----------------------------------Contributors----------------------------------

Mateusz Cybruk: Initial implementation, design, and documentation
