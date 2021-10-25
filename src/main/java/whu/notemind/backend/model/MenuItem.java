package whu.notemind.backend.model;


public class MenuItem {

    private String path;

    private String name;

    private boolean isDirectory;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public MenuItem(String path, String name, boolean isDirectory) {
        this.path = path;
        this.name = name;
        this.isDirectory = isDirectory;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MenuItem) {
             MenuItem menuItem = (MenuItem) obj;
             return name.equalsIgnoreCase(menuItem.getName().trim());
         }
         return false;
    }
}
