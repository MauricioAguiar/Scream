/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.monteiro.ads.projeto2.scream.controllers;

import br.ifpb.monteiro.ads.projeto2.scream.beans.facade.GenericBeanIF;
import br.ifpb.monteiro.ads.projeto2.scream.entities.Identifiable;
import br.ifpb.monteiro.ads.projeto2.scream.exceptions.ScreamException;
import br.ifpb.monteiro.ads.projeto2.scream.util.jsf.JsfUtil;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 *
 * @author Mauricio
 * @param <T>
 */
public abstract class GenericController<T extends Identifiable> implements GenericControllerIF {

    /**
     *
     */
    private List<Identifiable> items = null;
    private static final Logger logger = Logger.getGlobal();

    public GenericController() {
    }

    public abstract T getSelected();

    public abstract void setSelected(T selected);

    protected abstract GenericBeanIF getBeans();

    public void create() {
        try {
            logger.info("Controller Create Acessado");
            this.getBeans().create(getSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ItemCreated"));
            throw new ScreamException(); //Verificar se isso realemente é assim ^^

        } catch (ScreamException e) {
            logger.info("Controller Create Errado");
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
        }

    }

    public void update() {
        try {

            this.getBeans().edit(getSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ItemUpdated"));
            throw new ScreamException();

        } catch (ScreamException e) {

            JsfUtil.addErrorMessage(e.getLocalizedMessage());
        }
    }

    public void destroy() {
        try {

            this.getBeans().remove(getSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ItemDeleted"));
            if (!JsfUtil.isValidationFailed()) {
                setSelected(null);
                items = null;
            }
            throw new ScreamException();

        } catch (ScreamException e) {

            JsfUtil.addErrorMessage(e.getLocalizedMessage());
        }

    }

    public List<Identifiable> getItems() {
        if (items == null) {
            items = getBeans().findAll();
        }
        return items;
    }

    public Identifiable getItem(Long id) {
        return getBeans().find(id);
    }

    public List<Identifiable> getItemsAvailableSelectMany() {
        return getBeans().findAll();
    }

    public List<Identifiable> getItemsAvailableSelectOne() {
        return getBeans().findAll();
    }
}
