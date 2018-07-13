package com.mcmc.ray.scan.beans;

import java.util.List;

public class OrderBean {
    private String name;
    private String factory;
    private int total;
    private List<Project> project;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Project> getProject() {
        return project;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }


    public static class Project{
        private String name;
        private String sn;
        private String vendor;
        private List<Attribute> attribute;
        private List<Procedure> procedure;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public List<Attribute> getAttribute() {
            return attribute;
        }

        public void setAttribute(List<Attribute> attribute) {
            this.attribute = attribute;
        }

        public List<Procedure> getProcedure() {
            return procedure;
        }

        public void setProcedure(List<Procedure> procedure) {
            this.procedure = procedure;
        }
    }

    public static class Attribute{
        private String name;
        private String sn;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }
    }

    public static class Procedure{
        private String name;
        private String sn;
        private String vendor;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }
    }

}
