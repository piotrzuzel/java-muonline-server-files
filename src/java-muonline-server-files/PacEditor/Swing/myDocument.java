/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PacEditor.Swing;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;



/**
 *
 * @author Miki
 */
public class myDocument implements Document{

    @Override
    public int getLength() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addDocumentListener(DocumentListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeDocumentListener(DocumentListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addUndoableEditListener(UndoableEditListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeUndoableEditListener(UndoableEditListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getProperty(Object key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putProperty(Object key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getText(int offset, int length) throws BadLocationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void getText(int offset, int length, Segment txt) throws BadLocationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Position getStartPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Position getEndPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Position createPosition(int offs) throws BadLocationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Element[] getRootElements() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Element getDefaultRootElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(Runnable r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
