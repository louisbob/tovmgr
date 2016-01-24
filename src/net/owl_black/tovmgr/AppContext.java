package net.owl_black.tovmgr;

public final class AppContext {

	private static volatile AppContext instance = null;
	
	protected CoreGUI coreGUI;
	protected VMGModel vmgModel;
	protected VMGController vmgController;
	
	public static void main(String[] args) {
    	//Create new application context
    	AppContext ac = AppContext.getInstance();
    	
    	//Instantiate objects (order's important!)
    	ac.setVmgModel(new VMGModel());
    	ac.setVmgController(new VMGController());
    	ac.setCoreGUI(new CoreGUI());
    	ac.launchApplication();
	}
	
	private AppContext() {
		
	}
	
	public void launchApplication() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                coreGUI.setVisible(true);
            }
        });
	}
	
	public final static AppContext getInstance() {
        //Le "Double-Checked Singleton"/"Singleton doublement v�rifi�" permet 
        //d'�viter un appel co�teux � synchronized, 
        //une fois que l'instanciation est faite.
        if (AppContext.instance == null) {
           // Le mot-cl� synchronized sur ce bloc emp�che toute instanciation
           // multiple m�me par diff�rents "threads".
           // Il est TRES important.
           synchronized(AppContext.class) {
             if (AppContext.instance == null) {
            	 AppContext.instance = new AppContext();
             }
           }
        }
        
        return AppContext.instance;
    }
	
	//Getter and Setter
	public CoreGUI getCoreGUI() {
		return coreGUI;
	}

	public VMGModel getVmgModel() {
		return vmgModel;
	}

	public VMGController getVmgController() {
		return vmgController;
	}
	
	public void setCoreGUI(CoreGUI coreGUI) {
		this.coreGUI = coreGUI;
	}

	public void setVmgModel(VMGModel vmgModel) {
		this.vmgModel = vmgModel;
	}

	public void setVmgController(VMGController vmgController) {
		this.vmgController = vmgController;
	}
}
