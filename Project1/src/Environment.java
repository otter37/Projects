import java.io.*;
import java.util.Scanner;

public class Environment {
	//Stephen Ott - Programming Project 1 - 4-2-2016
	private int rows;
	private int columns;
	private Cell[][] grid;
	
	public Environment(String file) {
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e){
			System.out.println("File not found.");
			System.exit(0);
		}
		rows= fileIn.nextInt();
		columns = fileIn.nextInt();
		
		grid = new Cell[rows][columns];
		
		for(int i = 0; i < rows; i++) {
			for( int j = 0; j<columns; j++) {
				if(fileIn.nextInt() ==1){
					grid[i][j] = new Cell(true);
				}
				else {
					grid[i][j] = new Cell(false);
				}
			}
		}
		
		fileIn.close();
	

}
	
	public void runSimulation() {
		StdDraw.setCanvasSize(500,500);
		StdDraw.setPenRadius(.005);
		int run = 0;
		while(true) {
		run++;	
		StdDraw.clear();
		drawCells();
		StdDraw.setPenColor(StdDraw.BLACK);
		drawGrid();
		StdDraw.show(1000);
		updateCells();
		System.out.println("Run #" + run);
		}

		
	}

	private void drawGrid() {
		double numberRows = rows;
		double numberColumns = columns;
		for(int i = 0; i < rows; i++) {
			StdDraw.line(0, i/numberRows, 1, i/numberRows);
		}
		for(int i = 0; i < columns; i++) {
			StdDraw.line(i/numberColumns, 0, i/numberColumns, 1);
		}
	}
	
	
	private void drawCells() {
		double numberRows = rows;
		double numberColumns = columns;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(grid[i][j].getOccupied() == true) {
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.filledRectangle(j/numberColumns+1/numberColumns/2, 1-(i/numberRows)-1/numberRows/2, 1/numberRows/2, 1/numberColumns/2);
				}
			}
		}
		
	}
	
	private void updateCells() {
		Cell[][] newGrid = new Cell[rows][columns];
		for(int i=0;i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				int neighbors = 0;
				
				//Check all cells around current and calculate number of neighbors
				if(i-1>=0 && j-1 >=0 && grid[i-1][j-1].getOccupied() == true){
					neighbors ++;
				}
				if(i-1>=0 && grid[i-1][j].getOccupied() == true){
					neighbors ++;
				}
				if(i-1>=0 && j+1 < columns && grid[i-1][j+1].getOccupied() == true){
					neighbors ++;
				}
				if(j+1 < columns && grid[i][j+1].getOccupied() == true){
					neighbors ++;
				}
				if(j-1 >=0 && grid[i][j-1].getOccupied() == true){
					neighbors ++;
				}
				if(i+1 < rows && j-1 >=0 && grid[i+1][j-1].getOccupied() == true){
					neighbors ++;
				}
				if(i+1 < rows && grid[i+1][j].getOccupied() == true){
					neighbors ++;
				}
				if(i+1 < rows && j+1 < columns && grid[i+1][j+1].getOccupied() == true){
					neighbors ++;
				}
				
				//Empty cell and exactly 3 neighbors = new birth
				if(grid[i][j].getOccupied() == false && neighbors == 3) {
					newGrid[i][j] = new Cell(true);
				}
				
				//Empty cell and not 3 neighbors = keep as is
				if(grid[i][j].getOccupied() == false && neighbors != 3) {
					newGrid[i][j] = new Cell(false);
				}
				
				//Living cell and <2 neighbors, dies of loneliness
				if(grid[i][j].getOccupied() == true && neighbors < 2) {
					newGrid[i][j] = new Cell(false);
				}
				
				//Living cell, more than 3 neighbors, dies of overcrowding
				if(grid[i][j].getOccupied() == true && neighbors > 3) {
					newGrid[i][j] = new Cell(false);
				}
				
				//Either 2 or 3 neighbors, stays alive
				if(grid[i][j].getOccupied() == true && neighbors == 2 || neighbors == 3) {
					newGrid[i][j] = new Cell(true);
				}
			}
		}
		//Replace grid with new cells
		grid = newGrid;
		
	}

}

	
