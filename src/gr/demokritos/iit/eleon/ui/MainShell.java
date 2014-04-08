package gr.demokritos.iit.eleon.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.List;

public class MainShell extends Shell {
	protected Text text;
	protected String endpoint;
	protected Table table;
	protected Tree tree;
	protected List list;
	static protected MainShell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			shell = new MainShell(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public MainShell(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.NONE);
		mntmFile.setText("File");
		
		MenuItem mntmAbout = new MenuItem(menu, SWT.NONE);
		mntmAbout.setText("About");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(92, 10, 811, 24);
		
		Button btnLoad = new Button(this, SWT.NONE);
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				endpoint = text.getText();
				clearListTreeTavle();
				fillList();
				fillTree();
				fillTable();
				
			}
		});
		btnLoad.setBounds(909, 10, 99, 27);
		btnLoad.setText("Load");
		
		Label lblEnpoint = new Label(this, SWT.NONE);
		lblEnpoint.setBounds(10, 10, 76, 18);
		lblEnpoint.setText("Enpoint");
		
		Label lbltree = new Label(this, SWT.NONE);
		lbltree.setBounds(318, 54, 283, 18);
		lbltree.setText("Tree");
		
		Label lblfacet = new Label(this, SWT.NONE);
		lblfacet.setBounds(10, 54, 76, 18);
		lblfacet.setText("Facet");
		
		Label lblFields = new Label(this, SWT.NONE);
		lblFields.setBounds(607, 54, 76, 18);
		lblFields.setText("Fields");
		
		list = new List(this, SWT.BORDER);
		list.setBounds(10, 79, 302, 583);
		
		tree = new Tree(this, SWT.BORDER);
		tree.setBounds(318, 79, 283, 583);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(607, 79, 401, 583);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		createContents();
	}
	
	protected void fillTree() {
		TreeItem child1 = new TreeItem(tree, SWT.NONE, 0);
		child1.setText("1");
		TreeItem child2 = new TreeItem(tree, SWT.NONE, 1);
		child2.setText("2");
		TreeItem child2a = new TreeItem(child2, SWT.NONE, 0);
		child2a.setText("2A");
		TreeItem child2b = new TreeItem(child2, SWT.NONE, 1);
		child2b.setText("2B");
		TreeItem child3 = new TreeItem(tree, SWT.NONE, 2);
		child3.setText("3");
	}
	
	protected void fillTable() {
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText("New TableItem");
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(198);
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
	}
	
	protected void fillList() {
		String[] listItems = {"item1", "item2", "item3"};
		list.setItems(listItems);
	}
	
	protected void clearListTreeTavle() {
		list.dispose();
		list = new List(this, SWT.BORDER);
		list.setBounds(10, 79, 302, 583);
		tree.dispose();
		tree = new Tree(this, SWT.BORDER);
		tree.setBounds(318, 79, 283, 583);
		table.dispose();
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(607, 79, 401, 583);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Eleon");
		setSize(1024, 739);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
