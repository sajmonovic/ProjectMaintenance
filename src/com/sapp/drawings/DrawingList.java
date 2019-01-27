package com.sapp.drawings;

import java.util.*;

/** The object contains a list of Drawing objects grouped by ID into separate revision lists
 *
 */

public class DrawingList {

    private Map<String, RevisionList> list = new TreeMap<>();

    public void add(Drawing drawing) throws DuplicateRevisionException {
        if (!list.containsKey(drawing.getId())) {
            // if list doesn't contain the drawing Id, create a new entry
            RevisionList newEntry = new RevisionList();
            newEntry.add(drawing);

            list.put(drawing.getId(), newEntry);
        } else {
            // if it does contain it, add a new Drawing object to the list associated with the specific ID
            List<Drawing> entry = list.get(drawing.getId());

            // if the entry does not contain the specified revision number (see overridden equals() method from Drawing class)
            if (!entry.contains(drawing)) {
                entry.add(drawing);
                // sort list

                Collections.sort(entry, Drawing.getComparator());
            } else {
                throw new DuplicateRevisionException(drawing.getId() + " -> " + drawing.getTitle() + " -> " + drawing.getRevision());
            }
        }
    }

    public Map<String, RevisionList> getList() {
        return this.list;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (Map.Entry<String, RevisionList> entry : list.entrySet()){
            sb.append("-> ");
            sb.append(entry.getKey());
            sb.append("\n");

            for (Drawing d : entry.getValue()) {
                    sb.append("  > Revision: ");
                    sb.append(d.getRevision());
                    sb.append(" > Title : ");
                    sb.append(d.getTitle());
                    sb.append(" > Has secondary: ");
                    sb.append(d.hasSecondary());
                    sb.append("\n");
            }
        }
        return sb.toString();
    }
}
