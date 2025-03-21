/*
 * Persephone: An Agent-Based Modeling Platform
 * Copyright (c) 2019-2021  David Galloway / University of Pittsburgh
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package edu.pitt.gallowdd.persephone.container;

import edu.pitt.gallowdd.persephone.PersephoneException;

/**
 * The exception class that will be thrown if we try to add an Id to a container that is 
 * of a mixing role type that is not accepted by the container.
 * 
 * E.g. the container does not accept agents in the role of EMPLOYEE
 *
 * @author David Galloway
 */
public class MixingContainerTypeMatchException extends PersephoneException {

  private static final long serialVersionUID = 1L;

  /**
   * Default Constructor
   * 
   * @param agentId the ID of the agent
   * @param mixingRoleType the MixingRoleType of the agent
   */
  public MixingContainerTypeMatchException(String agentId, MixingRoleTypeEnum mixingRoleType)
  {
    super(ErrorCode.ERR_MXG_CNTNR_ROLE_TYPE_MATCH.message(agentId, mixingRoleType.toString()));
  }
}
