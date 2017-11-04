package org.cs.parshwabhoomi.server.dto;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient //makes the fields inline into subclasses as if this xml binding doesn't exist. but 400
public class AbstractRequestDTO implements DTO {
}
