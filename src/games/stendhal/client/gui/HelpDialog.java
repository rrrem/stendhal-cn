package games.stendhal.client.gui;

import games.stendhal.client.SpriteStore;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * A help system that displays the manual
 * 
 * @author hendrik (mostly based on "How to Use Trees"
 *         http://java.sun.com/docs/books/tutorial/uiswing/components/tree.html)
 */
public class HelpDialog extends JFrame {
	private static final long serialVersionUID = 41013220176906825L;

	private JEditorPane htmlPane;
	private JTree tree;
	private URL helpURL;

	/**
	 * Creates a new help dialog
	 */
	class HelpDialogPanel extends JPanel implements TreeSelectionListener {
	public HelpDialogPanel() {
		super(new GridLayout(1, 0));

		// Create the nodes.
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Stendhal Manual");
		createNodes(top);

		// Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		// Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);

		// Create the HTML viewing pane.
		htmlPane = new JEditorPane();
		htmlPane.setEditable(false);
		initHelp();
		JScrollPane htmlView = new JScrollPane(htmlPane);

		// Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(50, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(250);
		splitPane.setPreferredSize(new Dimension(790, 580));

		// Add the split pane to this panel.
		add(splitPane);
	}

	/**
	 * Update the browser window on selection change in the tree
	 */
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			BookInfo book = (BookInfo) nodeInfo;
			displayURL(book.bookURL);
		} else {
			displayURL(helpURL);
		}
	}

	private class BookInfo {
		private String bookName;
		private URL bookURL;

		private BookInfo(String book, String filename) {
			bookName = book;
			bookURL = SpriteStore.get().getResourceURL("data/docu/" + filename);
			if (bookURL == null) {
				System.err.println("Couldn't find file: " + filename);
			}
		}

		@Override
		public String toString() {
			return bookName;
		}
	}

	private void initHelp() {
		String filename = "introduction.html";
		helpURL = SpriteStore.get().getResourceURL("data/docu/" + filename);
		if (helpURL == null) {
			System.err.println("Couldn't open help file: " + filename);
		}
		displayURL(helpURL);
	}

	private void displayURL(URL url) {
		try {
			if (url != null) {
				htmlPane.setPage(url);
			} else { // null url
				htmlPane.setText("File Not Found");
			}
		} catch (IOException e) {
			System.err.println("Attempted to read a bad URL: " + url);
		}
	}

	private void createNodes(DefaultMutableTreeNode top) {
		top.add(new DefaultMutableTreeNode(new BookInfo("Introduction", "introduction.html")));
		top.add(new DefaultMutableTreeNode(new BookInfo("Setting up the game", "setting.html")));
		top.add(new DefaultMutableTreeNode(new BookInfo("Controls and Game settings", "controls.html")));
		top.add(new DefaultMutableTreeNode(new BookInfo("Gameplay", "gameplay.html")));
	}
	}
	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 *
	 * @param parent 
	 */
	public HelpDialog() {
		// Create and set up the window.
		super("Stendhal - Help");
		super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		HelpDialogPanel newContentPane = new HelpDialogPanel();
		newContentPane.setOpaque(true); // content panes must be opaque
		super.setContentPane(newContentPane);

		// Display the window.
		super.pack();
		super.setVisible(true);
	}

	/**
	 * debug method
	 * 
	 * @param args
	 *            ignores
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		new HelpDialog();
	}

}
